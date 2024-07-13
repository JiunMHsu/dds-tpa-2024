package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Getter;

@Getter
public class SuscripcionFaltaVianda extends Suscripcion implements ISuscipcionMovimientoVianda {

  private Integer viandasRestantes;

  public SuscripcionFaltaVianda(Colaborador colaborador,
                                Heladera heladera,
                                MedioDeNotificacion medioDeNotificacion,
                                Integer viandasRestantes) {
    super(colaborador, heladera, medioDeNotificacion);
    this.viandasRestantes = viandasRestantes;
  }

  @Override
  public void suscribirAHeladera() {
  }

  public void serNotificado(Integer cantViandas) {
    if (cantViandas <= viandasRestantes) {
      this.notificarAColaborador("");
    }
  }

}
