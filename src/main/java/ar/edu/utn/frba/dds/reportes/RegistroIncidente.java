package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class RegistroIncidente {
  public static Map<String, Integer> incidentesPorHeladera;

  public RegistroIncidente() {
    incidentesPorHeladera = new HashMap();
  }

  public static void incidentePorHeladeras(Heladera heladera) {
    incidentesPorHeladera.put(heladera.getNombre(), incidentesPorHeladera.getOrDefault(heladera.getNombre(), 0) + 1);
  }
}
