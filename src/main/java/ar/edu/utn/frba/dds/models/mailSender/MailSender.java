package ar.edu.utn.frba.dds.models.mailSender;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    private String nombreUsuario;
    private String contrasenia;
    private String host; // de un supuesto servidor
    private String port; // de un supuesto servidor

    public MailSender(String nombreUsuario, String contrasenia, String host, String port) {

        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.host = host;
        this.port = port;
    }
    public void enviarMail(String destinatario, String asunto, String cuerpo) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host); // Para el supuesto servidor
        props.put("mail.smtp.port", port); // Para el supuesto servidor

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(nombreUsuario, contrasenia);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);

            System.out.println("Correo enviado satisfactoriamente :D");

        } catch (MessagingException error) {
            error.printStackTrace();
            System.err.println("Error al enviar el mail D: : " + error.getMessage());
        }
    }

    // ACLARACION: Por ahora no hago la logica para recibir los mails, no parece ser necesario en base al enunciado
}
