package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.repository.incidente.IncidenteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroIncidente {

    private IncidenteRepository incidenteRepository;

    public RegistroIncidente() {
        this.incidenteRepository = new IncidenteRepository();
    }

    public Map<String, Integer> incidentesPorHeladera() {
//TODO ver repositorio de incidente
        LocalDate haceUnaSemana = LocalDate.now().minusWeeks(1);
        List<Incidente> incidentesDeLaSemana = new ArrayList<>();
                //incidenteRepository.obtenerAPartirDe(haceUnaSemana.atStartOfDay());

        Map<String, Integer> incidentesPorHeladera = new HashMap<>();
        for (Incidente incidente : incidentesDeLaSemana) {
            int cantidad = incidentesPorHeladera.getOrDefault(incidente.getHeladera().getNombre(), 0) + 1;
            incidentesPorHeladera.put(incidente.getHeladera().getNombre(), cantidad);
        }

        return incidentesPorHeladera;
    }
}
