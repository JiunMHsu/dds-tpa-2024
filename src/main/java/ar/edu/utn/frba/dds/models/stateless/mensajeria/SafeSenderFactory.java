package ar.edu.utn.frba.dds.models.stateless.mensajeria;

import ar.edu.utn.frba.dds.models.stateless.mensajeria.mail.SafeMailSender;

/**
 * Factory class for creating ISender objects.
 */
public class SafeSenderFactory implements ISenderFactory {
  @Override
  public ISender create() {
    return new SafeMailSender();
  }

  @Override
  public ISender create(MedioDeNotificacion medioDeNotificacion) {
    return switch (medioDeNotificacion) {
      case WHATSAPP -> new SafeMailSender();
      case TELEGRAM -> new SafeMailSender();
      case EMAIL -> new SafeMailSender();
      case TELEFONO -> new SafeMailSender(); // SMS??
    };
  }
}
