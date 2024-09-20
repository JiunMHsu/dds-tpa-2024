package ar.edu.utn.frba.dds.cargaDeColaboraciones;

import ar.edu.utn.frba.dds.mensajeria.EmailSender;
import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import ar.edu.utn.frba.dds.repository.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.repository.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.utils.GeneradorDeCredenciales;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CargadorDeColaboraciones implements WithSimplePersistenceUnit {

    private final EmailSender mailSender;
    private final ColaboradorRepository colaboradorRepository;
    private final DistribucionViandasRepository distribucionViandasRepository;
    private final DonacionDineroRepository donacionDineroRepository;
    private final DonacionViandaRepository donacionViandaRepository;
    private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private final MensajeRepository mensajeRepository;

    public CargadorDeColaboraciones(EmailSender mailSender) {
        this.mailSender = mailSender;
        this.colaboradorRepository = new ColaboradorRepository();
        this.distribucionViandasRepository = new DistribucionViandasRepository();
        this.donacionDineroRepository = new DonacionDineroRepository();
        this.donacionViandaRepository = new DonacionViandaRepository();
        this.repartoDeTarjetasRepository = new RepartoDeTarjetasRepository();
        this.mensajeRepository = new MensajeRepository();
    }

    // TODO - Revisar el retorno en caso de error
    // Por ahora dejo este mensajitos, pero se deberia ver la respuesta el controller... (cuando tengamos uno...)
    public void cargarColaboraciones(Path csv) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        CSVParser csvParser;

        try {
            csvParser = CSVParser.parse(
                    Files.newBufferedReader(csv),
                    CSVFormat.Builder.create().setHeader().build()
            );
        } catch (IOException error) {
            System.err.println("Error al leer el archivo CSV: " + error.getMessage());
            return;
        }

        try {
            for (CSVRecord csvRecord : csvParser) {
                Documento documento = new Documento(
                        csvRecord.get("Documento"),
                        TipoDocumento.valueOf(csvRecord.get("Tipo Doc"))
                );

                ColaboracionPrevia colaboracionPrevia = ColaboracionPrevia.of(
                        documento,
                        csvRecord.get("Nombre"),
                        csvRecord.get("Apellido"),
                        csvRecord.get("Mail"),
                        LocalDate.parse(csvRecord.get("Fecha de colaboración"), formatter),
                        csvRecord.get("Forma de colaboración"),
                        Integer.parseInt(csvRecord.get("Cantidad"))
                );

                Optional<Colaborador> colaborador = colaboradorRepository
                        .buscarPorEmail(colaboracionPrevia.getEmail());

                // TODO - reimplementar teniendo en cuenta el optional (por el momento solo compila, la lógica es erronea)

                if (colaborador.isEmpty()) {
                    Usuario usuario = GeneradorDeCredenciales.generarUsuario(
                            colaboracionPrevia.getNombre(),
                            colaboracionPrevia.getEmail()
                    );

                    Colaborador nuevoColaborador = Colaborador.colaborador(usuario);
                    colaboradorRepository.guardar(nuevoColaborador);
                    // TODO - Guardar usuario también?
                    this.enviarCredencial(nuevoColaborador);
                }

                this.registrarColaboracion(colaboracionPrevia, colaborador.get());
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo CSV: " + e.getMessage());
        }
    }

    private void registrarColaboracion(ColaboracionPrevia colaboracionPrevia,
                                       Colaborador colaborador) {

        switch (colaboracionPrevia.getFormaDeColaboracion()) {
            case "DINERO":
                DonacionDinero donacionDinero = DonacionDinero.por(
                        colaborador,
                        // TODO - Revisar manejo de fecha
                        colaboracionPrevia.getFechaDeColaboracion().atStartOfDay(),
                        colaboracionPrevia.getCantidad());
                donacionDineroRepository.guardar(donacionDinero);
                break;

            case "DONACION_VIANDAS":
                for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
                    DonacionVianda donacionVianda = DonacionVianda.por(
                            colaborador,
                            // TODO - Revisar manejo de fecha
                            colaboracionPrevia.getFechaDeColaboracion().atStartOfDay());
                    donacionViandaRepository.guardar(donacionVianda);
                }
                break;

            case "REDISTRIBUCION_VIANDAS":
                DistribucionViandas distribucionViandas = DistribucionViandas.por(
                        colaborador,
                        // TODO - Revisar manejo de fecha
                        colaboracionPrevia.getFechaDeColaboracion().atStartOfDay(),
                        colaboracionPrevia.getCantidad());
                distribucionViandasRepository.guardar(distribucionViandas);
                break;

            case "ENTREGA_TARJETAS":
                for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
                    RepartoDeTarjetas repartoDeTarjetas = RepartoDeTarjetas.por(
                            colaborador,
                            // TODO - Revisar manejo de fecha
                            colaboracionPrevia.getFechaDeColaboracion().atStartOfDay());
                    repartoDeTarjetasRepository.guardar(repartoDeTarjetas);
                }
                break;

            default:
                break;
        }
    }

    private void enviarCredencial(Colaborador colaborador) {

        Usuario usuario = colaborador.getUsuario();

        String asunto = "Credencial de usuario";
        String cuerpo = "Esta es la credencial:"
                + " - Nombre de usuario provicional: " + usuario.getNombre()
                + " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

        Mensaje mensaje = Mensaje.con(asunto, cuerpo, colaborador);
        mensaje.setMedio(MedioDeNotificacion.EMAIL);

        mailSender.enviarMensaje(colaborador.getContacto(), mensaje.getAsunto(), mensaje.getCuerpo());
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensajeRepository.agregar(mensaje);
    }
}
