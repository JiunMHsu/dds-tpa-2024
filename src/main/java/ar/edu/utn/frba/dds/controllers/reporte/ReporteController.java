package ar.edu.utn.frba.dds.controllers.reporte;

import ar.edu.utn.frba.dds.dtos.reporte.ReporteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import io.javalin.http.Context;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public void index(Context context) {
        List<Reporte> reportes = this.reporteService.buscarTodas();

        List<ReporteDTO> reporteDTO = reportes.stream()
                .map(ReporteDTO::completa)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("reportes", reporteDTO);

        context.render("reportes/reportes.hbs", model);
    }

    public void show(Context context) {
        String reporteId = context.pathParam("id");
        Reporte reporte = this.reporteService
                .buscarPorId(reporteId)
                .orElseThrow(ResourceNotFoundException::new);

        try {
            InputStream pdf = reporteService.buscarReporte(reporte);

            context.contentType("application/pdf");
            context.result(pdf);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
}
