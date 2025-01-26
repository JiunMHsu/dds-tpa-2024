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
import lombok.Setter;

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

  @Setter
  @Column(name = "es_entregada", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean esEntregada;

  public static Vianda con(Comida comida, LocalDate fechaCaducidad, Integer peso) {
    return Vianda.builder()
        .comida(comida)
        .fechaCaducidad(fechaCaducidad)
        .peso(peso)
        .esEntregada(false)
        .build();
  }
}
