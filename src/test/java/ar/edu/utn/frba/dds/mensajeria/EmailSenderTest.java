package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.mensajeria.EmailSender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailSenderTest {

  @Test
  public void testEmailSender() {
    EmailSender mailSender = new EmailSender();
    Mensaje mensaje = Mensaje.con(Contacto.conEmail("jhsu@frba.utn.edu.ar"),"TEST DDS EMAIL SENDER","test");
    try {
      mailSender.enviarMensaje(mensaje);
    } catch (MessagingException e) {
      Assertions.fail();
    }
  }

}
