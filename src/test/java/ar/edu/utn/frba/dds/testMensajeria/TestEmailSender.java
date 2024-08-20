package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.EmailSender;
import ar.edu.utn.frba.dds.models.data.Mail;
import org.junit.jupiter.api.Test;

public class TestEmailSender {

  @Test
  public void testEmailSender() {
    EmailSender mailSender = new EmailSender(
        "utn.dds.g22@gmail.com",
        "wvyp iozi gzes lfxb",
        "smtp.gmail.com",
        "587");

    mailSender.enviarMail(Mail.to("utn.dds.g22@gmail.com", "Test Mail Sender", "test"));
  }
}
