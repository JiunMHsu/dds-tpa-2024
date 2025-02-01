package ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create Distribucion Viandas DTO.
 */
@Getter
@AllArgsConstructor
public class CreateDistribucionViandasDTO {
  private final String heladeraOrigen;
  private final String heladeraDestino;
  private final int cantViandas;
  private final String motivo;
}
