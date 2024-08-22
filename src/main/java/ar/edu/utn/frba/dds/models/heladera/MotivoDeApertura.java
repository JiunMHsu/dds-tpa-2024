package ar.edu.utn.frba.dds.models.heladera;

import lombok.Getter;

@Getter
public class MotivoDeApertura {
  private final String descripcion;

  public MotivoDeApertura(String descripcion) {
    this.descripcion = descripcion;
  }
}
