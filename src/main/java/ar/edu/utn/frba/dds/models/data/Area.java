package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Area {
  private Ubicacion ubicacion;
  private Double radio;

  public Area(Ubicacion ubicacion, Double radio) {
    this.ubicacion = ubicacion;
    this.radio = radio;
  }

  public double calcularDistanciaAUbicacion(Ubicacion ubicacion2){
    double distanciaEntreUbicaciones = ubicacion.calcularDistanciaEntreUbicaciones(ubicacion2);
    return distanciaEntreUbicaciones - radio;
  }
}
