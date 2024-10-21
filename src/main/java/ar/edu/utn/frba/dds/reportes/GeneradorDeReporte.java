package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import ar.edu.utn.frba.dds.utils.AppProperties;
import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlFragment;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Builder;

@Builder
public class GeneradorDeReporte {

    private final ReporteService reporteService;
    private final ScheduledExecutorService planificador;

    public GeneradorDeReporte(ReporteService reporteService) {
        this.reporteService = reporteService;
        this.planificador = Executors.newScheduledThreadPool(1);
    }
    public void planificar(int frecuencia, TimeUnit unidadDeFrecuencia) {
        planificador.scheduleAtFixedRate(reporteService::generarReporteSemanal, 0, frecuencia, unidadDeFrecuencia);
    }
}
