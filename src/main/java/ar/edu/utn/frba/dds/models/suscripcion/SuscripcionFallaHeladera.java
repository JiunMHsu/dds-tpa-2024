package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;

public class SuscripcionFallaHeladera extends Suscripcion {

  public SuscripcionFallaHeladera(Colaborador colaborador,
                                  Heladera heladera,
                                  MedioDeNotificacion medioDeNotificacion) {
    super(colaborador, heladera, medioDeNotificacion);
  }

  @Override
  public void suscribirAHeladera() {
    // getHeladera().serSuscriptoPor(this);
  }

  // TODO
  public void serNotificado() {
    this.notificarAColaborador("La Heradera " + getHeladera().getNombre() + " sufrió fallas");
  }

}
