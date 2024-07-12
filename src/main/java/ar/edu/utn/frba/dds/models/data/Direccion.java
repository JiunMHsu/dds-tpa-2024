package ar.edu.utn.frba.dds.models.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Direccion {
  private Calle calle;
  private Integer altura;
  private Ubicacion ubicacion;

  public static Direccion with(Calle calle, Integer altura, Ubicacion ubicacion) {
    return Direccion
        .builder()
        .calle(calle)
        .altura(altura)
        .ubicacion(ubicacion)
        .build();
  }

  public static Direccion with(Calle calle, Integer altura) {
    return Direccion
        .builder()
        .calle(calle)
        .altura(altura)
        .build();
  }
}
