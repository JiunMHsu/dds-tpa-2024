package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DistribucionViandasRepository {
  private static final List<DistribucionViandas> db = new ArrayList<>();

  public static void agregar(DistribucionViandas colaboracion) {
    db.add(colaboracion);
  }

  public static List<DistribucionViandas> obtenerPorColaborador(Colaborador colaborador) {
    return db.stream()
        .filter(colab -> colab.getColaborador().equals(colaborador))
        .toList();
  }

  public static List<DistribucionViandas> obtenerPorColaboradorAPartirDe(Colaborador colaborador,
                                                                         LocalDate fecha) {
    if(fecha != null){
      return DistribucionViandasRepository.obtenerPorColaborador(colaborador).stream()
              .filter(colab -> colab.getFechaDistribucion().isAfter(fecha))
              .toList();
    } else{
      return DistribucionViandasRepository.obtenerPorColaborador(colaborador);
    }
  }

}
