package ar.edu.utn.frba.dds.models.data;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import lombok.Getter;

@Getter
public class Contacto {
  private String email;
  private String telefono;
  private String whatsApp;

  public Contacto(String email, String telefono, String whatsApp) {
    this.email = email;
    this.telefono = telefono;
    this.whatsApp = whatsApp;
  }

  public String getContacto(MedioDeNotificacion medioDeNotificacion) {
    return switch (medioDeNotificacion) {
      case WHATSAPP -> this.getWhatsApp();
      case TELEGRAM -> null;
      case MAIL -> this.getEmail();
    };
  }
}