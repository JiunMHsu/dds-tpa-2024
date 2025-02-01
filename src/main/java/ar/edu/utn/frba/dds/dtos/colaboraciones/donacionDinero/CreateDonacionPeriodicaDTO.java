package ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de donación de dinero periódica.
 */
@Getter
@AllArgsConstructor
public class CreateDonacionPeriodicaDTO {
  private int monto;
  private int periodicidad;

  // DAY, WEEK, MONTH, YEAR
  private String unidadPeriodicidad;
}
