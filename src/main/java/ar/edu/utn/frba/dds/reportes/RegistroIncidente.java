package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class RegistroIncidente {
  public static Map<String, Integer> incidentesPorHeladera = new HashMap();

  public static void incidentePorHeladeras(Heladera heladera) {
    incidentesPorHeladera.put(heladera.getNombre(), incidentesPorHeladera.getOrDefault(heladera.getNombre(), 0) + 1);
  }
}
