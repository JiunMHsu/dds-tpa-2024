package ar.edu.utn.frba.dds.mensajeria;

public class NotificadorFactory {

  public static Notificador of(MedioDeNotificacion medioDeNotificacion) {
    return switch (medioDeNotificacion) {
      case WHATSAPP -> new WhatsAppSender();
      case TELEGRAM -> new TelegramSender();
      case MAIL -> new MailSender();
    };
  }
}
