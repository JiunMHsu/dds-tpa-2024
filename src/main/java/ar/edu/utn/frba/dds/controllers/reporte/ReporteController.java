package ar.edu.utn.frba.dds.controllers.reporte;

import ar.edu.utn.frba.dds.dtos.reporte.ReporteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import io.javalin.http.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        //TODO verificar rol de admin
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
            throw new ResourceNotFoundException("No se encontró reporte con id " + reporteId);

        ReporteDTO reporteDTO = ReporteDTO.completa(reporte.get());
        File pdfFile = new File(reporteDTO.getPathToPdf());

        if (!pdfFile.exists() || !pdfFile.isFile()) {
            throw new ResourceNotFoundException("El archivo PDF no se encontró en la ruta " + reporteDTO.getPathToPdf());
        }

        try (FileInputStream fis = new FileInputStream(pdfFile)) {
            context.contentType("application/pdf");
            context.result(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo PDF", e);
        }
    }


}
