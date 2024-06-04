package ar.edu.utn.frba.dds.models.heladera;

import lombok.Getter;

@Getter
public class RangoTemperatura {
  private Double temperaturaMaxima;
  private Double temperaturaMinima;

  public RangoTemperatura(Double temperaturaMaxima, Double temperaturaMinima) {
    this.temperaturaMaxima = temperaturaMaxima;
    this.temperaturaMinima = temperaturaMinima;
  }
}
