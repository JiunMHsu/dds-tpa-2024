package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.incidente.AlertaDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
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

public class AlertaController extends UserRequired {

  private final IncidenteService incidenteService;
  private final VisitaTecnicaService visitaTecnicaService;

  public AlertaController(UsuarioService usuarioService,
                          IncidenteService incidenteService,
                          VisitaTecnicaService visitaTecnicaService) {
    super(usuarioService);
    this.incidenteService = incidenteService;
    this.visitaTecnicaService = visitaTecnicaService;
  }

  public void index(Context context) {
    List<Incidente> incidentes = this.incidenteService.buscarTodasAlertas();

    List<AlertaDTO> alertasDTOS = incidentes.stream()
        .map(AlertaDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("alertas", alertasDTOS);

    render(context, "incidentes/alertas.hbs", model);
  }

  public void show(Context context) {
    String alertaId = context.pathParam("id");

    Incidente alerta = this.incidenteService.buscarIncidentePorId(alertaId)
        .orElseThrow(ResourceNotFoundException::new);

    List<VisitaTecnicaDTO> visitasPreviasDTO = this.visitaTecnicaService
        .buscarPorincidente(alerta)
        .stream().map(VisitaTecnicaDTO::preview)
        .toList();

    Heladera heladera = alerta.getHeladera();

    boolean puedeResolver = rolFromSession(context).isTecnico() && !alerta.getEsResuelta();

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", HeladeraDTO.preview(heladera));
    model.put("alerta", AlertaDTO.completa(alerta));
    model.put("puedeResolver", puedeResolver);

    model.put("visitasPrevias", visitasPreviasDTO);

    render(context, "incidentes/alerta_detalle.hbs", model);
  }

}
