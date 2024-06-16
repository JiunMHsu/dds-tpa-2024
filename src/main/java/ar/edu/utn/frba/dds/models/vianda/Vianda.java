package ar.edu.utn.frba.dds.models.vianda;

import ar.edu.utn.frba.dds.models.data.Comida;

import java.time.LocalDate;
import lombok.Getter;

@Getter

public class Vianda {
  private Comida comida;
  private LocalDate fechaCaducidad;
  private Integer peso;

  public Vianda(Comida comida, LocalDate fechaCaducidad, Integer peso) {
    this.comida = comida;
    this.fechaCaducidad = fechaCaducidad;
    this.peso = peso;
  }
}
