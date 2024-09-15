package ar.edu.utn.frba.dds.models.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class Area {
  @Embedded
  private final Ubicacion ubicacion;

  @Column(name = "radio")
  private final Double radio;

  @Column (name = "barrio")
  private final Barrio barrio;

  public Area(Ubicacion ubicacion, Double radio, Barrio barrio) {
    this.ubicacion = ubicacion;
    this.radio = radio;
    this.barrio = barrio;
  }

  public Area() {
    this.ubicacion = null;
    this.radio = null;
    this.barrio = null;
  }

  public double calcularDistanciaAUbicacion(Ubicacion ubicacion2) {
    double distanciaEntreUbicaciones = ubicacion.calcularDistanciaEntreUbicaciones(ubicacion2);
    return distanciaEntreUbicaciones - radio;
  }
}
