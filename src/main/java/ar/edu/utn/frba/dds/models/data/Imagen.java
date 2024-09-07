package ar.edu.utn.frba.dds.models.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Imagen {

  @Column(name = "ruta", columnDefinition = "TEXT")
  private String ruta;

  public Imagen(String ruta) {
    this.ruta = ruta;
  }

  public Imagen() {
    this.ruta = null;
  }
}
