package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Contacto {
  private Mail email;
  private String telefono;
  private String whatsApp;

  public Contacto(Mail email, String telefono, String whatsApp) {
    this.email = email;
    this.telefono = telefono;
    this.whatsApp = whatsApp;
  }
}