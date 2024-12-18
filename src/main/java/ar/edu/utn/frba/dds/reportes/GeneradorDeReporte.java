package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import ar.edu.utn.frba.dds.utils.AppProperties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GeneradorDeReporte {

  private final ReporteService reporteService;
  private final ScheduledExecutorService planificador;

  public GeneradorDeReporte(ReporteService reporteService) {
    this.reporteService = reporteService;
    this.planificador = Executors.newScheduledThreadPool(1);
  }

  public void planificar(int frecuencia, TimeUnit unidadDeFrecuencia) {
    String dir = AppProperties.getInstance().propertyFromName("REPORT_DIR");
    PDFGenerator generator = new PDFGenerator(dir);
    planificador.scheduleAtFixedRate(
        () -> reporteService.generarReporteSemanal(generator),
        0,
        frecuencia,
        unidadDeFrecuencia);
  }
}
