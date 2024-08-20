package ar.edu.utn.frba.dds.models.data;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Contacto {
  private String email;
  private String telefono;
  private String whatsApp;
  private String telegram;

  public static Contacto with(String email, String telefono, String whatsApp, String telegram) {
    return Contacto
        .builder()
        .email(email)
        .telefono(telefono)
        .whatsApp(whatsApp)
        .telegram(telegram)
        .build();
  }

  public static Contacto ofTelegram(String telegram) {
    return Contacto
        .builder()
        .telegram(telegram)
        .build();
  }

  public static Contacto ofWhatsApp(String whatsApp) {
    return Contacto
        .builder()
        .whatsApp(whatsApp)
        .build();
  }

  public static Contacto empty() {
    return Contacto
        .builder().build();
  }

  public String getContacto(MedioDeNotificacion medioDeNotificacion) {
    return switch (medioDeNotificacion) {
      case WHATSAPP -> this.getWhatsApp();
      case TELEGRAM -> this.getTelegram();
      case EMAIL -> this.getEmail();
    };
  }
}