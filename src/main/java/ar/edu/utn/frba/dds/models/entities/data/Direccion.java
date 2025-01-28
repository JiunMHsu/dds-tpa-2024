package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Representa una dirección.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Direccion {

  @Embedded
  private Barrio barrio;

  @Embedded
  private Calle calle;

  @Column(name = "altura")
  private Integer altura;

  @Embedded
  private Ubicacion ubicacion;

  /**
   * Constructor de una dirección.
   *
   * @param barrio    Barrio de la dirección.
   * @param calle     Calle de la dirección.
   * @param altura    Altura de la dirección.
   * @param ubicacion Ubicación de la dirección.
   * @return Dirección creada.
   */
  public static Direccion con(Barrio barrio, Calle calle, Integer altura, Ubicacion ubicacion) {
    return Direccion.builder()
        .barrio(barrio)
        .calle(calle)
        .altura(altura)
        .ubicacion(ubicacion)
        .build();
  }

  /**
   * Constructor de una dirección.
   *
   * @param calle  Calle de la dirección.
   * @param altura Altura de la dirección.
   * @return Dirección creada.
   */
  public static Direccion con(Calle calle, Integer altura) {
    return Direccion.builder().calle(calle).altura(altura).build();
  }

  /**
   * Constructor de una dirección.
   *
   * @param barrio Barrio de la dirección.
   * @param calle  Calle de la dirección.
   * @param altura Altura de la dirección.
   * @return Dirección creada.
   */
  public static Direccion con(Barrio barrio, Calle calle, Integer altura) {
    return Direccion.builder().barrio(barrio).calle(calle).altura(altura).build();
  }

  /**
   * Obtiene la dirección en formato cadena.
   *
   * @return Dirección en formato texto.
   */
  @Override
  public String toString() {
    return this.calle.getNombre() + " " + this.altura.toString() + ", " + this.barrio.getNombre();
  }
}
