package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.heladera.AperturaHeladera;
import java.util.ArrayList;
import java.util.List;

public class AperturaHeladeraRepository {
  private static final List<AperturaHeladera> db = new ArrayList<>();

  public static void agregar(AperturaHeladera apertura) {
    db.add(0, apertura);
  }
  
}
