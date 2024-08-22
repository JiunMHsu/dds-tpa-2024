package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Comida {
  private final String nombre;
  private final Integer calorias;

  public Comida(String nombre, Integer calorias) {
    this.nombre = nombre;
    this.calorias = calorias;
  }
}