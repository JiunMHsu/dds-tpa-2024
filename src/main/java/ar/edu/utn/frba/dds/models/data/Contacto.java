package ar.edu.utn.frba.dds.models.data;

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
}