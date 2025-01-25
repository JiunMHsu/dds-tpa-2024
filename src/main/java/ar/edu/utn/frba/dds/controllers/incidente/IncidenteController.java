package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
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

public class IncidenteController extends UserRequired {

  private final IncidenteService incidenteService;

  public IncidenteController(UsuarioService usuarioService, IncidenteService incidenteService) {
    super(usuarioService);
    this.incidenteService = incidenteService;
  }

  public void index(Context context) {
    List<Incidente> incidentes = this.incidenteService.buscarTodos();

    List<IncidenteDTO> incidenteDTOS = incidentes.stream()
        .map(IncidenteDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", incidenteDTOS);

    render(context, "incidentes/incidentes.hbs", model);
  }

  public void show(Context context) {
    String incidenteId = context.pathParam("id");

    Incidente incidente = incidenteService
        .buscarIncidentePorId(incidenteId).orElseThrow(ResourceNotFoundException::new);

    if (Objects.requireNonNull(incidente.getTipo()).equals(TipoIncidente.FALLA_TECNICA)) {
      context.redirect("/fallas-tecnicas/" + incidenteId);
    } else {
      context.redirect("/alertas/" + incidenteId);
    }
  }

}
