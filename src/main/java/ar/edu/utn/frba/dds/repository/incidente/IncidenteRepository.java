package ar.edu.utn.frba.dds.repository.incidente;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import java.time.LocalDateTime;
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

  public static List<Incidente> obtenerAPartirDe(LocalDateTime fechaHora) {
    return db.stream()
        .filter(incidente -> incidente.getFechaHora().isAfter(fechaHora))
        .toList();
  }
}
