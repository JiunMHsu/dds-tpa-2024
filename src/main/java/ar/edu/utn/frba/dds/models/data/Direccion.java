package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Direccion {
  private Calle calle;
  private Integer altura;
  private Ubicacion ubicacion;

  public Direccion(Calle calle, Integer altura, Ubicacion ubicacion) {
    this.calle = calle;
    this.altura = altura;
    this.ubicacion = ubicacion;
  }
}
