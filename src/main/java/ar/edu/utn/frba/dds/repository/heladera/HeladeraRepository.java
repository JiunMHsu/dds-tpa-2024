package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;

import java.util.ArrayList;
import java.util.List;

public class HeladeraRepository {
  private static final List<Heladera> db = new ArrayList<>();
  
  public static void agregar(Heladera heladera) {db.add(heladera);}

  public static List<Heladera> obtenerTodos() {
    return db;
  }

  private static Heladera buscarPor(String nombre) {
    return null;
  }

}
