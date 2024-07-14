package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Ubicacion {
  private Double latitud;
  private Double longitud;

  public Ubicacion(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
}