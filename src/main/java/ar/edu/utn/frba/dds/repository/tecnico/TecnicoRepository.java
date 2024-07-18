package ar.edu.utn.frba.dds.repository.tecnico;

import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class TecnicoRepository {
  private static final List<Tecnico> db = new ArrayList<>();

  public static void agregar(Tecnico tecnico) {
    db.add(tecnico);
  }

  public static List<Tecnico> obtenerTodos() {
    return new ArrayList<>(db);
  }

}
