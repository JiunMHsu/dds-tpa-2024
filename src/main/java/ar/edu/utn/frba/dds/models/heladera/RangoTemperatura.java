package ar.edu.utn.frba.dds.models.heladera;

import lombok.Getter;

@Getter
public class RangoTemperatura {
  private Float temperaturaMaxima;
  private Float temperaturaMinima;

  public RangoTemperatura(Float temperaturaMaxima, Float temperaturaMinima) {
    this.temperaturaMaxima = temperaturaMaxima;
    this.temperaturaMinima = temperaturaMinima;
  }
}
