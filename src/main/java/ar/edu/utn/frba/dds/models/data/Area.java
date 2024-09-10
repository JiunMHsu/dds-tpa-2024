package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@Embeddable
public class Area {
  @Embedded
  private final Ubicacion ubicacion;

  @Column(name = "radio")
  private final Double radio;

  public Area(Ubicacion ubicacion, Double radio) {
    this.ubicacion = ubicacion;
    this.radio = radio;
  }

  public Area() {
    this.ubicacion = null;
    this.radio = null;
  }

  public double calcularDistanciaAUbicacion(Ubicacion ubicacion2) {
    double distanciaEntreUbicaciones = ubicacion.calcularDistanciaEntreUbicaciones(ubicacion2);
    return distanciaEntreUbicaciones - radio;
  }
}
