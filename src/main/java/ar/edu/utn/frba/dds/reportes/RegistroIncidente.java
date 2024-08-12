package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.repository.incidente.IncidenteRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class RegistroIncidente {

  public static Map<String, Integer> incidentesPorHeladera() {
    Map<String, Integer> incidentesPorHeladera = new HashMap<>();
    LocalDate haceUnaSemana = LocalDate.now().minusWeeks(1);

    List<Incidente> incidentesDeLaSemana = IncidenteRepository.obtenerAPartirDe(haceUnaSemana.atStartOfDay());

    for (Incidente incidente : incidentesDeLaSemana) {
      int cantidad = incidentesPorHeladera.getOrDefault(incidente.getHeladera().getNombre(), 0) + 1;
      incidentesPorHeladera.put(incidente.getHeladera().getNombre(), cantidad);
    }

    return incidentesPorHeladera;
  }
}
