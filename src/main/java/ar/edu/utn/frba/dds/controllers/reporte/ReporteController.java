package ar.edu.utn.frba.dds.controllers.reporte;

import ar.edu.utn.frba.dds.dtos.reporte.ReporteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReporteController {

    private ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public void index(Context context) {

        List<Reporte> reportes = this.reporteService.buscarTodas();

        List<ReporteDTO> reporteDTO = reportes.stream()
                .map(ReporteDTO::completa)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("Reportes", reporteDTO);

        context.render("reportes/reportes.hbs", model);
    }

    public void show(Context context) {
        String reporteId = context.pathParam("id");
        Optional<Reporte> reporte = this.reporteService.buscarPorId(reporteId);

        if (reporte.isEmpty())
            throw new ResourceNotFoundException("No se encontró reporte paraColaborador id " + reporteId);

        Map<String, Object> model = new HashMap<>();

        ReporteDTO reporteDTO = ReporteDTO.completa(reporte.get());
        model.put("heladera", reporteDTO);

        context.render("reportes/reportes_detalle.hbs", model);
    }

}
