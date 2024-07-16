package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.heladera.RetiroDeVianda;
import java.util.ArrayList;
import java.util.List;

public class RetiroDeViandaRepository {
  private static final List<RetiroDeVianda> db = new ArrayList<>();

  public static void agregar(RetiroDeVianda retiroDeVianda) {
    db.add(retiroDeVianda);
  }
}
