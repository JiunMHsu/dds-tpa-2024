package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.EmailSender;
import org.junit.jupiter.api.Test;

public class TestEmailSender {

  @Test
  public void testEmailSender() {
    EmailSender mailSender = new EmailSender();
    mailSender.enviarMensaje(
        "jhsu@frba.utn.edu.ar",
        "TEST DDS EMAIL SENDER",
        "test"
    );
  }

}
