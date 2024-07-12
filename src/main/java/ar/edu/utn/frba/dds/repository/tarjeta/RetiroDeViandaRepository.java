package ar.edu.utn.frba.dds.repository.tarjeta;

import ar.edu.utn.frba.dds.models.tarjeta.RetiroDeVianda;
import java.util.ArrayList;
import java.util.List;

public class RetiroDeViandaRepository {
  private static final List<RetiroDeVianda> db = new ArrayList<>();

  public static void agregar(RetiroDeVianda retiroDeVianda) {
    db.add(retiroDeVianda);
  }
}
