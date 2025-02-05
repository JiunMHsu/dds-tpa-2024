package ar.edu.utn.frba.dds.dtos.heladera;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para actualizar una heladera.
 * Por el momento solo se puede actualizar la temperatura mínima y máxima.
 */
@Getter
@AllArgsConstructor
public class UpdateHeladeraDTO {
  private final double tempMin;
  private final double tempMax;
}
