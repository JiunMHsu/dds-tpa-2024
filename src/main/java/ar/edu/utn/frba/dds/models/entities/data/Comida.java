package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Comida {

  @Column(name = "comida_nombre", nullable = false)
  private String nombre;

  @Column(name = "comida_calorias", nullable = false)
  private Integer calorias;

  public static Comida with(String nombre,
                            Integer calorias) {
    return Comida
        .builder()
        .nombre(nombre)
        .calorias(calorias)
        .build();
  }
}