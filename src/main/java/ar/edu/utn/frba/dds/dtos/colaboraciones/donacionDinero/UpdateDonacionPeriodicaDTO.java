package ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de actualización de donación de dinero periódica.
 */
@Getter
@AllArgsConstructor
public class UpdateDonacionPeriodicaDTO {
  String id;
  private int monto;
  private int periodicidad;
  private String unidadPeriodicidad; // DAY, WEEK, MONTH, YEAR
}
