package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepartoDeTarjetasRepository {
  private static final List<RepartoDeTarjetas> db = new ArrayList<>();

  public static void agregar(RepartoDeTarjetas repartoDeTarjetas) {
    db.add(repartoDeTarjetas);
  }

  public static List<RepartoDeTarjetas> obtenerPorColaborador(Colaborador colaborador) {
    return db.stream()
        .filter(colab -> colab.getColaborador().equals(colaborador))
        .toList();
  }

  public static List<RepartoDeTarjetas> obtenerPorColaboradorAPartirDe(Colaborador colaborador,
                                                                       LocalDate fecha) {
    return RepartoDeTarjetasRepository.obtenerPorColaborador(colaborador).stream()
        .filter(colab -> colab.getFechaReparto().isAfter(fecha))
        .toList();
  }
}
