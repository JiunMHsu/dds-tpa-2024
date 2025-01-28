package ar.edu.utn.frba.dds.models.stateless.mensajeria;

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
