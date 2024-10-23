package ar.edu.utn.frba.dds.services.Incidente;

import static ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente.FALLA_TECNICA;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import io.javalin.http.UploadedFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class IncidenteService {

    private final IncidenteRepository incidenteRepository;

    public IncidenteService(IncidenteRepository incidenteRepository) {
        this.incidenteRepository = incidenteRepository;
    }

    public List<Incidente> buscarIncidentes() {
        return this.incidenteRepository.obtenerTodos();
    }

    public List<Incidente> buscarIncidentesPorAlertas() {
        List<Incidente> todosLosIncidentes = buscarIncidentes();
        return todosLosIncidentes.stream()
            .filter(incidente -> !incidente.getTipo().equals(FALLA_TECNICA))
            .collect(Collectors.toList());
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

        this.incidenteRepository.guardar(incidente);
    }

    public List<Incidente> obtenerIncidentesAlertas() {
        return this.incidenteRepository.obtenerIncidentesAlertas();
    }

    public Optional<Incidente> buscarIncidentePorId(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del colaborador no puede ser null o vacío");
        }

        return this.incidenteRepository.buscarPorId(id);
    }


    public String guardarArchivo(UploadedFile uploadedFile) throws IOException {

        String uploadDir = "ruta/a/tu/directorio/de/subidas";
        Path path = Paths.get(uploadDir, uploadedFile.filename());

        Files.copy(uploadedFile.content(), path, StandardCopyOption.REPLACE_EXISTING);

        return path.toString();
    }

}
