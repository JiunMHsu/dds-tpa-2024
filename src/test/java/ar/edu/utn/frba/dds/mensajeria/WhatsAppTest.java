package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.mensajeria.WhatsAppSender;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WhatsAppTest {
  @Test
  public void enviarWhatsApp() {
    WhatsAppSender whatsAppSender = new WhatsAppSender();
    Mensaje mensaje = Mensaje.con(Contacto.conWhatsApp("whatsapp:+5491132420699"), "TEST DDS WHATSAPP SENDER",
        "test");
    try {
      whatsAppSender.enviarMensaje(mensaje);
    } catch (MessagingException e) {
      Assertions.fail();
    }
  }
}
