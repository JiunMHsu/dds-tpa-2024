package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Ubicacion {

  @Column(name = "latitud")
  private double latitud;

  @Column(name = "longitud")
  private double longitud;

  public double distanciaA(Ubicacion u2) {
    final int RADIO_TIERRA_KM = 6371;

    double lat1 = Math.toRadians(latitud);
    double lon1 = Math.toRadians(longitud);
    double lat2 = Math.toRadians(u2.getLatitud());
    double lon2 = Math.toRadians(u2.getLongitud());

    double dlat = lat2 - lat1;
    double dlon = lon2 - lon1;

    double a = Math.sin(dlat / 2) * Math.sin(dlat / 2)
        + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return RADIO_TIERRA_KM * c;
  }
}