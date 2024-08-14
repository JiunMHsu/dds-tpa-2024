package ar.edu.utn.frba.dds.repository.tarjeta;

import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import java.util.ArrayList;
import java.util.List;

public class TarjetaRepository {
  private static final List<Tarjeta> db = new ArrayList<>();

  public static void agregar(Tarjeta tarjeta) {
    db.add(tarjeta);
  }

  public static Tarjeta obtenerPorCodigo(String codigo) {
    return db.stream()
        .filter(tarjeta -> tarjeta.getCodigo().equals(codigo))
        .findAny()
        .orElse(null);
  }
}
