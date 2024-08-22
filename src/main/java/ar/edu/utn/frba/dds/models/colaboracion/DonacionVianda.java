package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class DonacionVianda {

  private Colaborador colaborador;
  private LocalDate fechaDonacion;
  private Vianda vianda;

  @Setter
  private Boolean esEntregada;

  public static DonacionVianda por(Colaborador colaborador,
                                   LocalDate fechaDonacion,
                                   Vianda vianda,
                                   Boolean esEntregada) {
    return DonacionVianda
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(fechaDonacion)
        .vianda(vianda)
        .esEntregada(esEntregada)
        .build();
  }

  public static DonacionVianda por(Colaborador colaborador,
                                   LocalDate fechaDonacion) {
    return DonacionVianda
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(fechaDonacion)
        .build();
  }

}