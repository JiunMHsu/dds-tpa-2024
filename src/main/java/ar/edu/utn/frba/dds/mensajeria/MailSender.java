package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.data.Mail;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.Getter;

@Getter
public class MailSender implements Notificador {

  private String nombreUsuario;
  private String contrasenia;
  private String host;
  private String port;

  public MailSender() {
    this.nombreUsuario = "";
    this.contrasenia = "";
    this.host = "";
    this.port = "";
  }

  @Override
  public void enviarMensaje(String mensaje) {

  }

  public void enviarMail(Mail content) {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);

    Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(nombreUsuario, contrasenia);
      }
    });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("your-email@example.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(content.getDestinatario()));
      message.setSubject(content.getAsunto());
      message.setText(content.getCuerpo());

      Transport.send(message);

      System.out.println("Correo enviado satisfactoriamente :D");

    } catch (MessagingException error) {
      error.printStackTrace();
      System.err.println("Error al enviar el mail D: : " + error.getMessage());
    }
  }
  
  // ACLARACION: Por ahora no hago la logica para recibir los mails, no parece ser necesario en base al enunciado
}
