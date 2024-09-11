package ar.edu.utn.frba.dds.models.data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "direccion")
public class Direccion {

  @Id
  @GeneratedValue
  private int id;

  @Embedded
  private Barrio barrio;

  @Embedded
  private Calle calle;

  @Column(name = "altura", nullable = false)
  private Integer altura;

  @Embedded
  private Ubicacion ubicacion; // Embedded

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
