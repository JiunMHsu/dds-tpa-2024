package ar.edu.utn.frba.dds.models.heladera;

import lombok.Getter;

@Getter
public class RangoTemperatura {
  private final Double maxima;
  private final Double minima;

  public RangoTemperatura(Double maxima, Double minima) {
    this.maxima = maxima;
    this.minima = minima;
  }

  public Boolean incluye(Double temperatura) {
    return (temperatura < maxima) && (temperatura > minima);
  }
}
