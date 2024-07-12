package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonacionDineroRepository {
  private static final List<DonacionDinero> db = new ArrayList<>();

  public static void agregar(DonacionDinero colaboracion) {
    db.add(colaboracion);
  }

  public static List<DonacionDinero> obtenerPorColaborador(Colaborador colaborador) {
    return db.stream()
        .filter(colab -> colab.getColaborador().equals(colaborador))
        .toList();
  }

  public static List<DonacionDinero> obtenerPorColaboradorAPartirDe(Colaborador colaborador,
                                                                    LocalDate fecha) {
    if(fecha != null){
      return DonacionDineroRepository.obtenerPorColaborador(colaborador).stream()
              .filter(colab -> colab.getFechaDonacion().isAfter(fecha))
              .toList();
    } else{
      return DonacionDineroRepository.obtenerPorColaborador(colaborador);
    }


  }

}
