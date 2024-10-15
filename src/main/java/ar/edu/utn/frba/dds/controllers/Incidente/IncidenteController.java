package ar.edu.utn.frba.dds.controllers.Incidente;

import ar.edu.utn.frba.dds.dtos.Incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.services.Incidente.IncidenteService;
import io.javalin.http.Context;

import java.util.List;
import java.util.stream.Collectors;

public class IncidenteController {

    private final IncidenteService incidenteService;

    public IncidenteController (IncidenteService incidenteService){
        this.incidenteService = incidenteService;
    }

    public void index(Context context) {

        List<Incidente> incidentes = this.incidenteService.buscarIncidentes();

        List<IncidenteDTO> i = incidentes.stream()
                .map(IncidenteDTO::completa)
                .collect(Collectors.toList());
    }

}
