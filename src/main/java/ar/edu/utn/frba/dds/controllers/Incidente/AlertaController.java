package ar.edu.utn.frba.dds.controllers.Incidente;

import ar.edu.utn.frba.dds.dtos.Incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.services.Incidente.IncidenteService;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlertaController {

    private final IncidenteService incidenteService;

    public AlertaController(IncidenteService incidenteService){
        this.incidenteService = incidenteService;
    }

    public void index(Context context) {

        List<Incidente> incidentes = this.incidenteService.buscarIncidentes();

        List<IncidenteDTO> incidentesDTOS = incidentes.stream()
                .map(IncidenteDTO::completa)
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("alertas", incidentesDTOS);
        model.put("titulo", "Alertas");

        context.render("alertas/alertas.hbs", model);
    }

    public void show(Context context) {

        Optional<Incidente> incidenteBuscado = this.incidenteService.buscarIncidentePorId(context.formParam("id"));

        if (incidenteBuscado.isEmpty()) {
            context.status(404).result("Incidente no encontrado");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidenteBuscado.get());

        // context.render();
    }



}
