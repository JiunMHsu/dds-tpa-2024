package ar.edu.utn.frba.dds.models.entities.vianda;

import ar.edu.utn.frba.dds.models.entities.data.Comida;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Modelo Vianda.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vianda")
public class Vianda extends EntidadPersistente {

  @Embedded
  private Comida comida;

  @Column(name = "fecha_caducidad", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaCaducidad;

  @Column(name = "peso", nullable = false)
  private Integer peso;

  /**
   * Crea una vianda.
   *
   * @param comida         {@link Comida} de la vianda
   * @param fechaCaducidad fecha de caducidad de la vianda
   * @param peso           peso de la vianda
   * @return vianda
   */
  public static Vianda con(Comida comida, LocalDate fechaCaducidad, Integer peso) {
    return Vianda.builder()
        .comida(comida)
        .fechaCaducidad(fechaCaducidad)
        .peso(peso)
        .build();
  }
}
