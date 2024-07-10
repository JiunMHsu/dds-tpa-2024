package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Comida {
  private String nombre;
  private Integer calorias;

  public Comida(String nombre, Integer calorias) {
    this.nombre = nombre;
    this.calorias = calorias;
  }
}