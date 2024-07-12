package ar.edu.utn.frba.dds.repository.colaborador;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorRepository {
  private static final List<Colaborador> db = new ArrayList<>();

  public static void agregar(Colaborador colaborador) {
    db.add(colaborador);
  }

  public static List<Colaborador> obtenerTodos() {
    return db; // ¿es una copia?
  }

  public static Colaborador obtenerPorEmail(String email) {
    return db.stream()
        .filter(colaborador -> colaborador.getUsuario().getEmail().equals(email))
        .findFirst()
        .orElse(null);
  }
}
