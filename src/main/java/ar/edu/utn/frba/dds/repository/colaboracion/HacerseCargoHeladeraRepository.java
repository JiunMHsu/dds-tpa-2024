package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.util.ArrayList;
import java.util.List;

public class HacerseCargoHeladeraRepository {
  private static final List<HacerseCargoHeladera> db = new ArrayList<>();

  private static void agregar(HacerseCargoHeladera colaboracion) {
    db.add(colaboracion);
  }

  public static List<HacerseCargoHeladera> obtenerPorColaborador(Colaborador colaborador) {
    return db.stream()
        .filter(colab -> colab.getColaborador().equals(colaborador))
        .toList();
  }

}
