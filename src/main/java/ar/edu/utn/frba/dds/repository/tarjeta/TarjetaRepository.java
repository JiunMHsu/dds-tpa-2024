package ar.edu.utn.frba.dds.repository.tarjeta;

import ar.edu.utn.frba.dds.models.tarjeta.ITarjeta;
import java.util.ArrayList;
import java.util.List;

public class TarjetaRepository {
  private static final List<ITarjeta> db = new ArrayList<>();

  public static void agregar(ITarjeta tarjeta) {
    db.add(tarjeta);
  }

  public static ITarjeta obtenerPorCodigo(String codigo) {
    return db.stream()
        .filter(tarjeta -> tarjeta.getCodigo().equals(codigo))
        .findAny()
        .orElse(null);
  }
}
