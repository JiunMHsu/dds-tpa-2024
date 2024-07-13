package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.time.Period;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonacionDinero {
  private Colaborador colaborador;
  private LocalDate fechaDonacion;
  private Integer monto;
  private Period frecuencia;

  public static DonacionDinero by(Colaborador colaborador, Integer monto, Period frecuencia) {
    return DonacionDinero
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(LocalDate.now())
        .monto(monto)
        .frecuencia(frecuencia)
        .build();
  }

  public static DonacionDinero by(Colaborador colaborador, LocalDate fechaDonacion, Integer monto) {
    return DonacionDinero
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(fechaDonacion)
        .monto(monto)
        .build();
  }

  public static DonacionDinero by(Colaborador colaborador, Integer monto) {
    return DonacionDinero
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(LocalDate.now())
        .monto(monto)
        .build();
  }


  public void setPeriod(Period frecuencia) {
    this.frecuencia = frecuencia;
  }
}