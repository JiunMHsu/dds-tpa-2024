package ar.edu.utn.frba.dds.models.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Comida {

  @Column(name = "comida_nombre", nullable = false)
  private String nombre;

  @Column(name = "comida_calorias", nullable = false)
  private Integer calorias;

  public Comida(String nombre, Integer calorias) {
    this.nombre = nombre;
    this.calorias = calorias;
  }

  public Comida() {
    this.nombre = null;
    this.calorias = null;
  }
}