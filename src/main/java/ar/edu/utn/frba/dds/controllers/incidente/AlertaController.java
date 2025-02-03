package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.incidente.AlertaDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.permissions.UserRequired;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de Alertas.
 */
public class AlertaController extends UserRequired {

  private final IncidenteService incidenteService;
  private final VisitaTecnicaService visitaTecnicaService;

  /**
   * Constructor.
   *
   * @param usuarioService       Servicio de usuario
   * @param incidenteService     Servicio de incidente
   * @param visitaTecnicaService Servicio de visita técnica
   */
  public AlertaController(UsuarioService usuarioService,
                          IncidenteService incidenteService,
                          VisitaTecnicaService visitaTecnicaService) {
    super(usuarioService);
    this.incidenteService = incidenteService;
    this.visitaTecnicaService = visitaTecnicaService;
  }

  /**
   * Muestra la lista de alertas.
   *
   * @param context Context de Javalin
   */
  public void index(Context context) {
    List<AlertaDTO> alertas = this.incidenteService.buscarTodasAlertas();

    Map<String, Object> model = new HashMap<>();
    model.put("alertas", alertas);
    render(context, "incidentes/alertas.hbs", model);
  }

  /**
   * Muestra el detalle de una alerta.
   * TODO: Revisar
   *
   * @param context Context de Javalin
   */
  public void show(Context context) {
    String alertaId = context.pathParam("id");

    Incidente alerta = this.incidenteService.buscarIncidentePorId(alertaId);

    List<VisitaTecnicaDTO> visitasPreviasDTO = this.visitaTecnicaService
        .buscarPorincidente(alerta)
        .stream().map(VisitaTecnicaDTO::preview)
        .toList();

    Heladera heladera = alerta.getHeladera();

    boolean puedeResolver = rolFromSession(context).isTecnico() && !alerta.getEsResuelta();

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", HeladeraDTO.preview(heladera));
    model.put("alerta", AlertaDTO.fromIncidente(alerta));
    model.put("puedeResolver", puedeResolver);

    model.put("visitasPrevias", visitasPreviasDTO);

    render(context, "incidentes/alerta_detalle.hbs", model);
  }

}
