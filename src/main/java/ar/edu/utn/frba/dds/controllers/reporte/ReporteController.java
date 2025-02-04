package ar.edu.utn.frba.dds.controllers.reporte;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.reporte.ReporteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de reportes.
 */
public class ReporteController extends ColaboradorRequired {

  private final ReporteService reporteService;

  /**
   * Constructor.
   *
   * @param reporteService servicio de reportes
   */
  public ReporteController(UsuarioService usuarioService,
                           ColaboradorService colaboradorService,
                           ReporteService reporteService) {
    super(usuarioService, colaboradorService);
    this.reporteService = reporteService;
  }

  /**
   * Muestra la lista de reportes.
   *
   * @param context contexto
   */
  public void index(Context context) {
    verifyPermission(context);

    List<ReporteDTO> reportes = this.reporteService.buscarTodas()
        .stream().map(ReporteDTO::completa).toList();

    Map<String, Object> model = new HashMap<>();
    model.put("reportes", reportes);
    render(context, "reportes/reportes.hbs", model);
  }

  /**
   * Muestra un reporte.
   *
   * @param context contexto
   */
  public void show(Context context) {
    verifyPermission(context);
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

  /**
   * Genera los reportes.
   *
   * @param context contexto
   */
  public void generate(Context context) {
    verifyPermission(context);
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      this.reporteService.generarReporteSemanal();
      redirects.add(new RedirectDTO("/reportes", "Ver Reportes Generados"));
      operationSuccess = true;

    } catch (Exception e) {
      redirects.add(new RedirectDTO("/reportes", "Volver"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);

      render(context, "post_result.hbs", model);
    }
  }

  private void verifyPermission(Context context) {
    TipoRol rol = this.rolFromSession(context);
    if (rol.isAdmin()) {
      return;
    }

    Colaborador colaborador = this.colaboradorFromSession(context);
    if (colaborador.getTipoColaborador().esHumano()) {
      throw new UnauthorizedException();
    }
  }
}
