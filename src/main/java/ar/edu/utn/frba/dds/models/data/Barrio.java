package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Barrio {
  private String nombre;

  public Barrio(String nombre) {
    this.nombre = nombre;
  }
}
