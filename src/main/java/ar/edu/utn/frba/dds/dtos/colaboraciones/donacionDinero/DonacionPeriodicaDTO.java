package ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDineroPeriodica;
import java.time.Period;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO de donación de dinero periódica.
 */
@Getter
@Builder
public class DonacionPeriodicaDTO {
  private String id;
  private String monto;
  private String periodicidad;

  /**
   * Crea un DTO de donación de dinero periódica.
   *
   * @param donacion donación de dinero periódica
   * @return DTO de donación de dinero periódica
   */
  public static DonacionPeriodicaDTO fromDonacionPeriodica(DonacionDineroPeriodica donacion) {
    return DonacionPeriodicaDTO.builder()
        .id(donacion.getId().toString())
        .monto(donacion.getMonto().toString())
        .periodicidad(parseFrecuencia(donacion.getFrecuencia()))
        .build();
  }

  private static String parseFrecuencia(Period frecuencia) {
    if (frecuencia.getDays() > 0) {
      String unidad = frecuencia.getDays() > 1 ? "Días" : "Día";
      return "Cada " + frecuencia.getDays() + " " + unidad;
    }

    if (frecuencia.getMonths() > 0) {
      String unidad = frecuencia.getMonths() > 1 ? "Meses" : "Mes";
      return "Cada " + frecuencia.getMonths() + " " + unidad;
    }

    if (frecuencia.getYears() > 0) {
      String unidad = frecuencia.getYears() > 1 ? "Años" : "Año";
      return "Cada " + frecuencia.getYears() + " " + unidad;
    }

    return "Nunca";
  }
}
