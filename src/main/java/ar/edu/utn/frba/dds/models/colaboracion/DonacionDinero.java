package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.time.Period;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class DonacionDinero {

  private Colaborador colaborador;
  private LocalDate fechaDonacion;
  private Integer monto;

  @Setter
  private Period frecuencia;

  public static DonacionDinero por(Colaborador colaborador,
                                   LocalDate fechaDonacion,
                                   Integer monto,
                                   Period frecuencia) {
    return DonacionDinero
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(fechaDonacion)
        .monto(monto)
        .frecuencia(frecuencia)
        .build();
  }

  public static DonacionDinero por(Colaborador colaborador,
                                   LocalDate fechaDonacion,
                                   Integer monto) {
    return DonacionDinero
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(fechaDonacion)
        .monto(monto)
        .build();
  }

}