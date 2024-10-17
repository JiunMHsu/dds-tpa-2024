package ar.edu.utn.frba.dds.services.Incidente;

import static ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente.FALLA_TECNICA;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IncidenteService {

    private final IncidenteRepository incidenteRepository;

    public IncidenteService(IncidenteRepository incidenteRepository) {
        this.incidenteRepository = incidenteRepository;
    }

    public List<Incidente> buscarIncidentes() {
        return this.incidenteRepository.obtenerTodos();
    }

    public Map<String, Integer> incidentesPorHeladera() {

        LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
        List<Incidente> incidentes = incidenteRepository.obtenerAPartirDe(haceUnaSemana);

        Map<String, Integer> incidentesPorHeladera = new HashMap<>();

        for (Incidente incidente : incidentes) {
            int cantidad = incidentesPorHeladera.getOrDefault(incidente.getHeladera().getNombre(), 0) + 1;
            incidentesPorHeladera.put(incidente.getHeladera().getNombre(), cantidad);
        }

        return incidentesPorHeladera;
    }

    public void guardarIncidente(Incidente incidente) {

        if (incidente.getTipo() == FALLA_TECNICA && incidente.getColaborador() == null) {
            throw new IllegalArgumentException("Las Fallas Tecnicas deben tener asociado un Colaborador");
        }

        incidenteRepository.guardar(incidente);
    }

    // Lo dejo basico x ahora
    public Optional<Incidente> buscarIncidentePorId(String id) {
        return Optional.empty(); // this.incidenteRepository.buscarPorId(id);
    }
}
