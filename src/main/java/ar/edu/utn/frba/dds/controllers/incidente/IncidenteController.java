package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import io.javalin.http.Context;

public class IncidenteController {

  private final IncidenteService incidenteService;

  public IncidenteController(IncidenteService incidenteService) {
    this.incidenteService = incidenteService;
  }

  public void index(Context context) {
//    List<Incidente> incidentes = this.incidenteService.buscarTodasAlertas();
//
//    List<AlertaDTO> alertasDTOS = incidentes.stream()
//        .map(AlertaDTO::preview)
//        .toList();
//
//    Map<String, Object> model = new HashMap<>();
//    model.put("alertas", alertasDTOS);
//
//    render(context, "alertas/alertas.hbs", model);
  }

  public void show(Context context) {
  }

}
