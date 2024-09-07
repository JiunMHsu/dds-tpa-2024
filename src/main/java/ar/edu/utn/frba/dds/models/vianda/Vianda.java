package ar.edu.utn.frba.dds.models.vianda;

import ar.edu.utn.frba.dds.models.data.Comida;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vianda")
public class Vianda {

  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;

  @Embedded
  private Comida comida;

  @Column(name = "fecha_caducidad", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaCaducidad;

  @Column(name = "peso", nullable = false)
  private Integer peso;

  public Vianda(Comida comida, LocalDate fechaCaducidad, Integer peso) {
    this.comida = comida;
    this.fechaCaducidad = fechaCaducidad;
    this.peso = peso;
  }
}
