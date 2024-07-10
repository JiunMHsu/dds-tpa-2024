package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Area {
  private Ubicacion ubicacion;
  private Integer radio;

  public Area(Ubicacion ubicacion, Integer radio) {
    this.ubicacion = ubicacion;
    this.radio = radio;
  }
}
