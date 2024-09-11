package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.List;

public class DonacionDineroRepository {
  public static void agregar(DonacionDinero colaboracion) {
  }

  public static List<DonacionDinero> obtenerPorColaborador(Colaborador colaborador) {
    return null;
  }

  public static List<DonacionDinero> obtenerPorColaboradorAPartirDe(Colaborador colaborador,
                                                                    LocalDate fecha) {
    return null;
  }

}
