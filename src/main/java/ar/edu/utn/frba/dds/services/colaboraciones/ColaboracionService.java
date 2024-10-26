package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.CargaMasivaException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.ColaboracionPrevia;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.mensajeria.ISender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.models.stateless.GeneradorDeCredenciales;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import jakarta.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ColaboracionService implements WithSimplePersistenceUnit {

    private final ISender mailSender;
    private final UsuarioRepository usuarioRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final DistribucionViandasRepository distribucionViandasRepository;
    private final DonacionDineroRepository donacionDineroRepository;
    private final DonacionViandaRepository donacionViandaRepository;
    private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private final MensajeRepository mensajeRepository;

    public ColaboracionService(ISender mailSender,
                               UsuarioRepository usuarioRepository,
                               ColaboradorRepository colaboradorRepository,
                               DistribucionViandasRepository distribucionViandasRepository,
                               DonacionDineroRepository donacionDineroRepository,
                               DonacionViandaRepository donacionViandaRepository,
                               RepartoDeTarjetasRepository repartoDeTarjetasRepository,
                               MensajeRepository mensajeRepository) {

        this.mailSender = mailSender;
        this.usuarioRepository = usuarioRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.distribucionViandasRepository = distribucionViandasRepository;
        this.donacionDineroRepository = donacionDineroRepository;
        this.donacionViandaRepository = donacionViandaRepository;
        this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
        this.mensajeRepository = mensajeRepository;
    }

    public void cargarColaboraciones(InputStream csv) throws CargaMasivaException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            BufferedReader reader = new BufferedReader(new InputStreamReader(csv, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create().setHeader().build());

            beginTransaction();
            for (CSVRecord csvRecord : csvParser) {
                Documento documento = new Documento(TipoDocumento.valueOf(csvRecord.get("Tipo Doc")), csvRecord.get("Documento"));

                ColaboracionPrevia colaboracionPrevia = ColaboracionPrevia.por(
                        documento,
                        csvRecord.get("Nombre"),
                        csvRecord.get("Apellido"),
                        csvRecord.get("Mail"),
                        LocalDate.parse(csvRecord.get("Fecha de colaboración"), formatter).atStartOfDay(),
                        csvRecord.get("Forma de colaboración"),
                        Integer.parseInt(csvRecord.get("Cantidad"))
                );

                Colaborador colaborador = colaboradorRepository
                        .buscarPorEmail(colaboracionPrevia.getEmail())
                        .orElse(generarColaborador(colaboracionPrevia.getNombre(), colaboracionPrevia.getEmail()));

                Mensaje mail = generarMensajePara(colaborador);
                mailSender.enviarMensaje(mail.getContacto(), mail.getAsunto(), mail.getCuerpo());
                mail.setFechaEnvio(LocalDateTime.now());
                mensajeRepository.guardar(mail);

                this.registrarColaboracion(colaboracionPrevia, colaborador);
            }
            commitTransaction();
        } catch (IOException | IllegalArgumentException | MessagingException e) {
            rollbackTransaction();
            throw new CargaMasivaException("Error al cargar las colaboraciones. Error: " + e.getMessage());
        }
    }

    private Colaborador generarColaborador(String nombre, String email) {
        Usuario usuario = GeneradorDeCredenciales.generarUsuario(nombre, email);
        Colaborador colaborador = Colaborador.colaborador(usuario);
        colaborador.setContacto(Contacto.conEmail(email));

        usuarioRepository.guardar(usuario);
        colaboradorRepository.guardar(colaborador);
        return colaborador;
    }

    private Mensaje generarMensajePara(Colaborador colaborador) {
        Usuario usuario = colaborador.getUsuario();

        String asunto = "Credencial por usuario";
        String cuerpo = "Esta es la credencial:"
                + " - Nombre por usuario provicional: " + usuario.getNombre()
                + " - Contrasenia por usuario provicional: " + usuario.getContrasenia();

        Mensaje mensaje = Mensaje.paraColaborador(colaborador, asunto, cuerpo);
        mensaje.setMedio(MedioDeNotificacion.EMAIL);

        return mensaje;
    }

    private void registrarColaboracion(ColaboracionPrevia colaboracionPrevia, Colaborador colaborador) {
        switch (colaboracionPrevia.getFormaDeColaboracion()) {
            case "DINERO":
                DonacionDinero donacionDinero = DonacionDinero
                        .por(colaborador, colaboracionPrevia.getFechaHora(), colaboracionPrevia.getCantidad());
                donacionDineroRepository.guardar(donacionDinero);
                break;

            case "DONACION_VIANDAS":
                for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
                    DonacionVianda donacionVianda = DonacionVianda
                            .por(colaborador, colaboracionPrevia.getFechaHora());
                    donacionViandaRepository.guardar(donacionVianda);
                }
                break;

            case "REDISTRIBUCION_VIANDAS":
                DistribucionViandas distribucionViandas = DistribucionViandas
                        .por(colaborador, colaboracionPrevia.getFechaHora(), colaboracionPrevia.getCantidad());
                distribucionViandasRepository.guardar(distribucionViandas);
                break;

            case "ENTREGA_TARJETAS":
                for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
                    RepartoDeTarjetas repartoDeTarjetas = RepartoDeTarjetas
                            .por(colaborador, colaboracionPrevia.getFechaHora());
                    repartoDeTarjetasRepository.guardar(repartoDeTarjetas);
                }
                break;

            default:
                break;
        }
    }
}
