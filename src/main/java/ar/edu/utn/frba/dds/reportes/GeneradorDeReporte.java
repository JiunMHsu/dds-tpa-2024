package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Generador de reportes.
 */
public class GeneradorDeReporte {

  private final ReporteService reporteService;
  private final ScheduledExecutorService planificador;

  public GeneradorDeReporte(ReporteService reporteService) {
    this.reporteService = reporteService;
    this.planificador = Executors.newScheduledThreadPool(1);
  }

  /**
   * Planifica la generación de reportes.
   *
   * @param frecuencia         Frecuencia de generación.
   * @param unidadDeFrecuencia Unidad de frecuencia.
   */
  public void planificar(int frecuencia, TimeUnit unidadDeFrecuencia) {
    planificador.scheduleAtFixedRate(
        reporteService::generarReporteSemanal,
        0,
        frecuencia,
        unidadDeFrecuencia);
  }
}
