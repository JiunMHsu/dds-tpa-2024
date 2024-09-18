package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.AppConfig;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.Getter;

@Getter
public class EmailSender implements Sender {

    private final String host;
    private final String port;
    private final String usuario;
    private final String contrasenia;

    public EmailSender(String host, String port, String nombreUsuario, String contrasenia) {
        this.host = host;
        this.port = port;
        this.usuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public EmailSender() {
        this.host = AppConfig.getProperty("EMAIL_HOST");
        this.port = AppConfig.getProperty("EMAIL_PORT");
        this.usuario = AppConfig.getProperty("EMAIL_USER");
        this.contrasenia = AppConfig.getProperty("EMAIL_PASSWORD");
    }

    @Override
    public void enviarMensaje(String receptor, String asunto, String cuerpo) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contrasenia);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);

            System.out.println("Correo enviado");

        } catch (MessagingException error) {
            error.printStackTrace();
            System.err.println("Error al enviar el mail: " + error.getMessage());
        }
    }

}
