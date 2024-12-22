package ar.edu.utn.frba.dds.models.entities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Area {

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "radio")
  private Integer radio;

  @Column(name = "barrio")
  private Barrio barrio;

  public static Area with(Ubicacion ubicacion,
                          Integer radio,
                          Barrio barrio) {
    return Area
        .builder()
        .ubicacion(ubicacion)
        .radio(radio)
        .barrio(barrio)
        .build();
  }

  public double distanciaA(Ubicacion unaUbicacion) {
    double distanciaEntreUbicaciones = this.ubicacion.distanciaA(unaUbicacion);
    return distanciaEntreUbicaciones - radio;
  }
}
