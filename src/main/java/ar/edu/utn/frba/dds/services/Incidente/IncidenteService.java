package ar.edu.utn.frba.dds.services.Incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import static ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidenteService {

    private final IncidenteRepository incidenteRepository;

    public IncidenteService (IncidenteRepository incidenteRepository) {
        this.incidenteRepository = incidenteRepository;
    }

    public List<Incidente> buscarIncidentes() { return this.incidenteRepository.obtenerTodos(); }

    public List<Incidente> buscarAlertas() {

        List<Incidente> alertas = new ArrayList<>();

        alertas.addAll(this.buscarFraudes());
        alertas.addAll(this.buscarFallasTemperatura());
        alertas.addAll(this.buscarFallasConexion());

        return alertas;
    }

    public List<Incidente> buscarFraudes() { return this.incidenteRepository.obtenerPorTipo(FRAUDE); }

    public List<Incidente> buscarFallasTemperatura() { return this.incidenteRepository.obtenerPorTipo(FALLA_TEMPERATURA); }

    public List<Incidente> buscarFallasConexion() { return this.incidenteRepository.obtenerPorTipo(FALLA_CONEXION); }

    public List<Incidente> buscarFallasTecnicas() { return this.incidenteRepository.obtenerPorTipo(FALLA_TECNICA); }

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

    public void guardarIncidente (Incidente incidente) {

        if (incidente.getTipo() == FALLA_TECNICA && incidente.getColaborador() == null) {
            throw new IllegalArgumentException("Las Fallas Tecnicas deben tener asociado un Colaborador");
        }

        incidenteRepository.guardar(incidente);
    }

}
