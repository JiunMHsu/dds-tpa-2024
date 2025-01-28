package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa una ubicación.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Ubicacion {

  @Column(name = "latitud")
  private double latitud;

  @Column(name = "longitud")
  private double longitud;

  /**
   * Calcula la distancia entre dos ubicaciones.
   *
   * @param ubicacion Ubicación a la que se quiere calcular la distancia.
   * @return Distancia entre las ubicaciones.
   */
  public double distanciaA(Ubicacion ubicacion) {
    final int radioTierraEnKm = 6371;

    double lat1 = Math.toRadians(latitud);
    double lon1 = Math.toRadians(longitud);
    double lat2 = Math.toRadians(ubicacion.getLatitud());
    double lon2 = Math.toRadians(ubicacion.getLongitud());

    double dlat = lat2 - lat1;
    double dlon = lon2 - lon1;

    double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
        + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return radioTierraEnKm * c;
  }
}