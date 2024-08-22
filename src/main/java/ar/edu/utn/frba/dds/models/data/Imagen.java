package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Imagen {
  private final String path;

  public Imagen(String path) {
    this.path = path;
  }
}
