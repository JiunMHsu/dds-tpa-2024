package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;

public class SuscripcionHeladeraLlena extends Suscripcion implements ISuscipcionMovimientoVianda {

  private Integer viandasFaltantes;

  public SuscripcionHeladeraLlena(Colaborador colaborador,
                                  Heladera heladera,
                                  MedioDeNotificacion medioDeNotificacion,
                                  Integer viandasFaltantes) {
    super(colaborador, heladera, medioDeNotificacion);
    this.viandasFaltantes = viandasFaltantes;
  }

  @Override
  public void suscribirAHeladera() {
  }

  public void serNotificado(Integer cantViandas) {
    int faltantes = this.getHeladera().getCapacidad() - cantViandas;
    if (faltantes <= viandasFaltantes) {
      this.notificarAColaborador("");
    }
  }

}
