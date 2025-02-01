package ar.edu.utn.frba.dds.dtos.colaboraciones.donacionVianda;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para la creación de una donación de vianda.
 */
@Getter
@AllArgsConstructor
public class CreateDonacionViandaDTO {
  private final String heladera;
  private final String nombreComida;
  private final int pesoVianda;
  private final int caloriasComida;
  private final LocalDate fechaCaducidadComida;
}
