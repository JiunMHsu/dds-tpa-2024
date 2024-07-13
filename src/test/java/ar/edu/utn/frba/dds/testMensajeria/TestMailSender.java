package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.MailSender;
import ar.edu.utn.frba.dds.models.data.Mail;
import org.junit.jupiter.api.Test;

public class TestMailSender {

  @Test
  public void testMailSender() {
    MailSender mailSender = new MailSender(
        "utn.dds.g22@gmail.com",
        "wvyp iozi gzes lfxb",
        "smtp.gmail.com",
        "587");

    mailSender.enviarMail(Mail.to("jhsu@frba.utn.edu.ar", "Test Mail Sender", "test"));
  }
}
