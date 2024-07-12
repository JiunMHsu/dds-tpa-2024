package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonacionViandaRepository {
  private static final List<DonacionVianda> db = new ArrayList<>();

  public static void agregar(DonacionVianda colaboracion) {
    db.add(colaboracion);
  }

  public static List<DonacionVianda> obtenerPorColaborador(Colaborador colaborador) {
    return db.stream()
        .filter(colab -> colab.getColaborador().equals(colaborador))
        .toList();
  }

  public static List<DonacionVianda> obtenerPorColaboradorAPartirDe(Colaborador colaborador,
                                                                    LocalDate fecha) {
    if(fecha != null){
      return DonacionViandaRepository.obtenerPorColaborador(colaborador).stream()
              .filter(colab -> colab.getFechaDonacion().isAfter(fecha))
              .toList();
    } else{
      return DonacionViandaRepository.obtenerPorColaborador(colaborador);
    }


  }

}
