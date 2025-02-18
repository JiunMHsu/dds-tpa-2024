package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.permissions.UserRequired;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controlador de Incidentes.
 */
public class IncidenteController extends UserRequired {

  private final IncidenteService incidenteService;

  /**
   * Constructor para inicializar el controlador de incidentes.
   *
   * @param usuarioService    El servicio de usuarios.
   * @param incidenteService  El servicio de incidentes.
   */
  public IncidenteController(UsuarioService usuarioService, IncidenteService incidenteService) {
    super(usuarioService);
    this.incidenteService = incidenteService;
  }

  /**
   * Muestra la lista de incidentes.
   *
   * @param context Context
   */
  public void index(Context context) {
    List<IncidenteDTO> incidentes = this.incidenteService.buscarTodos();

    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", incidentes);

    render(context, "incidentes/incidentes.hbs", model);
  }

  /**
   * Muestra un incidente.
   *
   * @param context Context
   */
  public void show(Context context) {
    String incidenteId = context.pathParam("id");
    Incidente incidente = incidenteService.buscarIncidentePorId(incidenteId);

    if (Objects.requireNonNull(incidente.getTipo()).equals(TipoIncidente.FALLA_TECNICA)) {
      context.redirect("/fallas-tecnicas/" + incidenteId);
    } else {
      context.redirect("/alertas/" + incidenteId);
    }
  }

}
