package ar.edu.utn.frba.dds.models.entities.heladera;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Rango de temperatura que puede tener una heladera.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RangoTemperatura {

  @Column(name = "temperatura_maxima", nullable = false)
  private Double maxima;

  @Column(name = "temperatura_minima", nullable = false)
  private Double minima;

  /**
   * Indica si la temperatura dada está dentro del rango.
   *
   * @param temperatura Temperatura a evaluar.
   * @return {@code true} si la temperatura está dentro del rango, {@code false} en caso contrario.
   */
  public boolean incluye(double temperatura) {
    return (maxima != null)
        && (minima != null)
        && (temperatura < maxima)
        && (temperatura > minima);
  }
}
