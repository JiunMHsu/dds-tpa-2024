package ar.edu.utn.frba.dds.cargaDeColaboraciones;

import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.Mail;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;
import ar.edu.utn.frba.dds.mensajeria.MailSender;
import ar.edu.utn.frba.dds.repository.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.repository.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.utils.GeneradorDeCredenciales;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CargadorDeColaboraciones {

  private MailSender mailSender;

  public CargadorDeColaboraciones(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void cargarColaboraciones(Path csv) {

    try {
      CSVParser csvParser = CSVParser.parse(Files.newBufferedReader(csv), CSVFormat.DEFAULT
          .withHeader("Tipo Doc",
              "Documento",
              "Nombre",
              "Apellido",
              "Mail",
              "Fecha de colaboración",
              "Forma de colaboración",
              "Cantidad")
          .withFirstRecordAsHeader());

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      for (CSVRecord csvRecord : csvParser) {

        Documento documento = new Documento(Integer.parseInt(csvRecord.get("Documento")),
            TipoDocumento.valueOf(csvRecord.get("Tipo Doc")));

        ColaboracionPrevia colaboracionPrevia = ColaboracionPrevia.of(
            documento,
            csvRecord.get("Nombre"),
            csvRecord.get("Apellido"),
            csvRecord.get("Mail"),
            LocalDate.parse(csvRecord.get("Fecha de colaboración"), formatter),
            csvRecord.get("Forma de colaboración"),
            Integer.parseInt(csvRecord.get("Cantidad"))
        );


        Colaborador colaborador = ColaboradorRepository.obtenerPorEmail(colaboracionPrevia.getEmail());

        if (colaborador == null) {
          Usuario usuario = GeneradorDeCredenciales.generarUsuario(
              colaboracionPrevia.getNombre(),
              colaboracionPrevia.getEmail());

          colaborador = Colaborador.colaborador(usuario);
          this.enviarCredencial(usuario);
        }
        this.registrarColaboracion(colaboracionPrevia, colaborador);
      }

    } catch (IOException error) {
      error.printStackTrace();
    }

  }

  private void registrarColaboracion(ColaboracionPrevia colaboracionPrevia, Colaborador colaborador) {

    switch (colaboracionPrevia.getFormaDeColaboracion()) {
      case "DINERO":
        DonacionDinero donacionDinero = DonacionDinero.by(
            colaborador,
            colaboracionPrevia.getFechaDeColaboracion(),
            colaboracionPrevia.getCantidad());
        DonacionDineroRepository.agregar(donacionDinero);
        break;

      case "DONACION_VIANDAS":
        for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
          DonacionVianda donacionVianda = DonacionVianda.by(
              colaborador,
              colaboracionPrevia.getFechaDeColaboracion());
          DonacionViandaRepository.agregar(donacionVianda);
        }
        break;

      case "REDISTRIBUCION_VIANDAS":
        DistribucionViandas distribucionViandas = DistribucionViandas.by(
            colaborador,
            colaboracionPrevia.getFechaDeColaboracion(),
            colaboracionPrevia.getCantidad());
        DistribucionViandasRepository.agregar(distribucionViandas);
        break;

      case "ENTREGA_TARJETAS":
        for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
          RepartoDeTarjetas repartoDeTarjetas = RepartoDeTarjetas.by(
              colaborador,
              colaboracionPrevia.getFechaDeColaboracion());
          RepartoDeTarjetasRepository.agregar(repartoDeTarjetas);
        }
        break;
    }

  }

  private void enviarCredencial(Usuario usuario) {
    String asunto = "Credencial de usuario";
    String cuerpo = "Esta es la credencial:"
        + " - Nombre de usuario provicional: " + usuario.getNombre()
        + " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

    mailSender.enviarMail(Mail.to(usuario.getEmail(), asunto, cuerpo));
  }
}
