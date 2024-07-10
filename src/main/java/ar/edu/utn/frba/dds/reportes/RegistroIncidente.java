package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RegistroIncidente {
  private static List<Incidente> incidentes = new ArrayList<>();

  public static void registrarIncidente(Incidente incidente) {
    RegistroIncidente.incidentes.add(incidente);
  }
}
