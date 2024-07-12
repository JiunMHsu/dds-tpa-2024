package ar.edu.utn.frba.dds.cargaDeColaboraciones;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.Mail;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;
import ar.edu.utn.frba.dds.mensajeria.MailSender;
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

  public static void cargarColaboraciones(Path csv) {

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
            LocalDate.parse(csvRecord.get("Fecha de colaboración"), formatter).atStartOfDay(),
            Colaboracion.valueOf(csvRecord.get("Forma de colaboración")),
            Integer.parseInt(csvRecord.get("Cantidad"))
        );

        CargadorDeColaboraciones.registrarColaboracion(colaboracionPrevia);
      }

    } catch (IOException error) {
      error.printStackTrace();
    }

  }

  // TODO
  private static void registrarColaboracion(ColaboracionPrevia colaboracionPrevia) {
    // Tratar de buscar si existe algún usuario que tenga dicho mail.
    // Si la hay, actualizar el colaborador asociado,
    // Si no, crear el usuario, el colaborador, cargar la colaboración
    // y enviar las credenciales generadas

    Usuario usuario = GeneradorDeCredenciales
        .generarUsuario(colaboracionPrevia.getNombre(), colaboracionPrevia.getEmail());

    CargadorDeColaboraciones.enviarCredencial(usuario);
  }

  public static void enviarCredencial(Usuario usuario) {
    String asunto = "Credencial de usuario";
    String cuerpo = "Esta es la credencial:"
        + " - Nombre de usuario provicional: " + usuario.getNombre()
        + " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

    MailSender.enviarMail(new MailSender(), new Mail(usuario.getEmail(), asunto, cuerpo));
  }
}
