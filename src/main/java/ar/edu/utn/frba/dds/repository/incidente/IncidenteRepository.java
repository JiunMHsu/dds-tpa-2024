package ar.edu.utn.frba.dds.repository.incidente;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import java.util.ArrayList;
import java.util.List;

public class IncidenteRepository {
  private static final List<Incidente> db = new ArrayList<>();

  public static void agregar(Incidente incidente) {
    db.add(incidente);
  }

  public static List<Incidente> obtenerTodos() {
    return new ArrayList<>(db);
  }
}
