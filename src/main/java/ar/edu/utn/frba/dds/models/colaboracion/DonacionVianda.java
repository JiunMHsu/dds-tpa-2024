package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import java.time.LocalDate;
import ar.edu.utn.frba.dds.reportes.RegistroDonacion;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonacionVianda {

  private Colaborador colaborador;
  private LocalDate fechaDonacion;
  private Vianda vianda;
  private Boolean esEntregada;

  public void agregarVianda(){
    RegistroDonacion.viandasPorColaborador(colaborador);
  }

  public static DonacionVianda by(Colaborador colaborador, Vianda vianda) {
    return DonacionVianda
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(LocalDate.now())
        .vianda(vianda)
        .build();
  }

  public static DonacionVianda by(Colaborador colaborador, LocalDate fechaDonacion) {
    return DonacionVianda
        .builder()
        .colaborador(colaborador)
        .fechaDonacion(fechaDonacion)
        .build();
  }
}