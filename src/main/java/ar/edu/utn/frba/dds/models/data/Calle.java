package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Calle {
  private final String nombre;

  public Calle(String nombre) {
    this.nombre = nombre;
  }
}
