package ar.edu.utn.frba.dds.models.entities.heladera;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RangoTemperatura {

  @Column(name = "temperatura_maxima", nullable = false)
  private Double maxima;

  @Column(name = "temperatura_minima", nullable = false)
  private Double minima;

  public Boolean incluye(Double temperatura) {
    return (maxima != null)
        && (minima != null)
        && (temperatura < maxima)
        && (temperatura > minima);
  }
}
