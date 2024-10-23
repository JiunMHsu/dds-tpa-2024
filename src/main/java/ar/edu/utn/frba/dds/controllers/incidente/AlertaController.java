package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.incidente.AlertaDTO;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertaController {

    private final IncidenteService incidenteService;

    public AlertaController(IncidenteService incidenteService) {
        this.incidenteService = incidenteService;
    }

    public void index(Context context) {
        List<Incidente> incidentes = this.incidenteService.buscarTodasAlertas();

        List<AlertaDTO> alertasDTOS = incidentes.stream()
                .map(AlertaDTO::preview)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("alertas", alertasDTOS);

        context.render("alertas/alertas.hbs", model);
    }

    public void show(Context context) {
        context.result("ALERTA ID: " + context.pathParam("id"));
    }


}
