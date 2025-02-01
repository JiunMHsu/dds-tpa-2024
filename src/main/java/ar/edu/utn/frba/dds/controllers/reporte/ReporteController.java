package ar.edu.utn.frba.dds.controllers.reporte;

import ar.edu.utn.frba.dds.dtos.reporte.ReporteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.permissions.UserRequired;
import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de reportes.
 */
public class ReporteController extends UserRequired {

  private final ReporteService reporteService;

  /**
   * Constructor.
   *
   * @param reporteService servicio de reportes
   */
  public ReporteController(UsuarioService usuarioService,
                           ReporteService reporteService) {
    super(usuarioService);
    this.reporteService = reporteService;
  }

  /**
   * Muestra la lista de reportes.
   *
   * @param context contexto
   */
  public void index(Context context) {
    List<ReporteDTO> reporte = this.reporteService.buscarTodas()
        .stream().map(ReporteDTO::completa).toList();

    Map<String, Object> model = new HashMap<>();
    model.put("reportes", reporte);
    render(context, "reportes/reportes.hbs", model);
  }

  /**
   * Muestra un reporte.
   *
   * @param context contexto
   */
  public void show(Context context) {
    String reporteId = context.pathParam("id");
    Reporte reporte = this.reporteService.buscarPorId(reporteId);

    try {
      InputStream pdf = reporteService.buscarReporte(reporte);

      context.contentType("application/pdf");
      context.result(pdf);
    } catch (FileNotFoundException e) {
      throw new ResourceNotFoundException();
    }
  }
}
