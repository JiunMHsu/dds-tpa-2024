package ar.edu.utn.frba.dds.models.heladera;

import lombok.Getter;

@Getter
public class RangoTemperatura {
  private Double maxima;
  private Double minima;

  public RangoTemperatura(Double maxima, Double minima) {
    this.maxima = maxima;
    this.minima = minima;
  }

  public Boolean incluye(Double temperatura) {
    return (temperatura < maxima) && (temperatura > minima);
  }
}
