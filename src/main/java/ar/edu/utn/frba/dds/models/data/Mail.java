package ar.edu.utn.frba.dds.models.data;

import ar.edu.utn.frba.dds.utils.ValidadorDeMail;
import lombok.Getter;

@Getter
public class Mail {
  private String address;

  private Mail(String address) {
    this.address = address;
  }

  public static Mail crear(String address) {
    if (ValidadorDeMail.esValido(address)) {
      return new Mail(address);
    }
    return null;
  }
}
