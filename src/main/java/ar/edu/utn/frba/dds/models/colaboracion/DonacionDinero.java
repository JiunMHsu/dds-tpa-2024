package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;

@Getter
public class DonacionDinero {

  private LocalDate fechaDonacion;
  private Integer monto;
  private Period frecuencia;
  private Colaborador colaborador;

  public DonacionDinero(Colaborador colaborador, Integer monto) {
    this.colaborador = colaborador;
    this.monto = monto;
    this.fechaDonacion = LocalDate.now();
    this.frecuencia = null;
  }

  public void setPeriod(Period frecuencia) {
    this.frecuencia = frecuencia;
  }
}