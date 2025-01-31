package ar.edu.utn.frba.dds.models.stateless.mensajeria;

import ar.edu.utn.frba.dds.models.stateless.mensajeria.mail.EmailSender;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.mail.SafeMailSender;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.telegram.TelegramSender;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.whatsApp.WhatsAppSender;

/**
 * Factory class for creating ISender objects.
 */
public class SenderFactory implements ISenderFactory {
  @Override
  public ISender create() {
    return new SafeMailSender();
  }

  @Override
  public ISender create(MedioDeNotificacion medioDeNotificacion) {
    return switch (medioDeNotificacion) {
      case WHATSAPP -> new WhatsAppSender();
      case TELEGRAM -> new TelegramSender();
      case EMAIL -> new EmailSender();
      case TELEFONO -> new SafeMailSender(); // SMS??
    };
  }
}
