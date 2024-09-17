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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CargadorDeColaboraciones implements WithSimplePersistenceUnit {

  private final EmailSender mailSender;
  private ColaboradorRepository colaboradorRepository;
  private DistribucionViandasRepository distribucionViandasRepository;
  private DonacionDineroRepository donacionDineroRepository;
  private DonacionViandaRepository donacionViandaRepository;
  private RepartoDeTarjetasRepository repartoDeTarjetasRepository;
  private MensajeRepository mensajeRepository;
  public CargadorDeColaboraciones(EmailSender mailSender) {
    this.mailSender = mailSender;
    this.colaboradorRepository = new ColaboradorRepository();
    this.distribucionViandasRepository=new DistribucionViandasRepository();
    this.donacionDineroRepository=new DonacionDineroRepository();
    this.donacionViandaRepository=new DonacionViandaRepository();
    this.repartoDeTarjetasRepository=new RepartoDeTarjetasRepository();
    this.mensajeRepository = new MensajeRepository();
  }

  // TODO - Revisar el retorno en caso de error
  public void cargarColaboraciones(Path csv) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    CSVParser csvParser;

    try {
      csvParser = CSVParser.parse(
          Files.newBufferedReader(csv),
          CSVFormat.Builder.create().setHeader().build()
      );
    } catch (IOException error) {
      error.printStackTrace();
      return;
    }

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

      Colaborador colaborador = colaboradorRepository
          .buscarPorEmail(colaboracionPrevia.getEmail());

      if (colaborador == null) {
        Usuario usuario = GeneradorDeCredenciales.generarUsuario(
            colaboracionPrevia.getNombre(),
            colaboracionPrevia.getEmail()
        );

        colaborador = Colaborador.colaborador(usuario);
        beginTransaction();
        colaboradorRepository.agregar(colaborador);
        this.enviarCredencial(usuario);
        commitTransaction();
      }

      this.registrarColaboracion(colaboracionPrevia, colaborador);
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
        beginTransaction();
        donacionDineroRepository.agregar(donacionDinero);
        commitTransaction();
        break;

      case "DONACION_VIANDAS":
        for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
          DonacionVianda donacionVianda = DonacionVianda.por(
              colaborador,
              // TODO - Revisar manejo de fecha
              colaboracionPrevia.getFechaDeColaboracion().atStartOfDay());
          beginTransaction();
          donacionViandaRepository.agregar(donacionVianda);
          commitTransaction();
        }
        break;

      case "REDISTRIBUCION_VIANDAS":
        DistribucionViandas distribucionViandas = DistribucionViandas.por(
            colaborador,
            // TODO - Revisar manejo de fecha
            colaboracionPrevia.getFechaDeColaboracion().atStartOfDay(),
            colaboracionPrevia.getCantidad());
        beginTransaction();
        distribucionViandasRepository.agregar(distribucionViandas);
        commitTransaction();
        break;

      case "ENTREGA_TARJETAS":
        for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
          RepartoDeTarjetas repartoDeTarjetas = RepartoDeTarjetas.por(
              colaborador,
              // TODO - Revisar manejo de fecha
              colaboracionPrevia.getFechaDeColaboracion().atStartOfDay());
          beginTransaction();
          repartoDeTarjetasRepository.agregar(repartoDeTarjetas);
          commitTransaction();
        }
        break;

      default:
        break;
    }
  }

  private void enviarCredencial(Usuario usuario) {

    String asunto = "Credencial de usuario";
    String cuerpo = "Esta es la credencial:"
        + " - Nombre de usuario provicional: " + usuario.getNombre()
        + " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

    Mensaje mensaje = Mensaje.con(asunto, cuerpo, usuario.getEmail());
    mensaje.setMedio(MedioDeNotificacion.EMAIL);

    mailSender.enviarMensaje(mensaje.getReceptor(), mensaje.getAsunto(), mensaje.getCuerpo());
    mensaje.setFechaEnvio(LocalDateTime.now());
    beginTransaction();
    mensajeRepository.agregar(mensaje);
    commitTransaction();
  }
}
