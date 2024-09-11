package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.List;

public class DonacionViandaRepository {
  public static void agregar(DonacionVianda colaboracion) {
  }

  public static List<DonacionVianda> obtenerPorColaborador(Colaborador colaborador) {
    return null;
  }

  public static List<DonacionVianda> obtenerPorColaboradorAPartirDe(Colaborador colaborador,
                                                                    LocalDate fecha) {
    return null;
  }

  public static List<DonacionVianda> obtenerAPartirDe(LocalDate fecha) {
    return null;
  }


}
