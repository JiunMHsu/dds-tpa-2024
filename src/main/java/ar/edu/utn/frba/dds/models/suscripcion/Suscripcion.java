package ar.edu.utn.frba.dds.models.suscripcion;

import ar.edu.utn.frba.dds.mensajeria.INotificador;
import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.mensajeria.NotificadorFactory;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Getter;

@Getter
public abstract class Suscripcion {

  private Colaborador colaborador;
  private Heladera heladera;
  private MedioDeNotificacion medioDeNotificacion;

  public Suscripcion(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion) {
    this.colaborador = colaborador;
    this.heladera = heladera;
    this.medioDeNotificacion = medioDeNotificacion;
  }

  public abstract void suscribirAHeladera();
  
  protected void notificarAColaborador(String mensaje) {
    INotificador notificador = NotificadorFactory.of(medioDeNotificacion);
    notificador.enviarMensaje(mensaje, colaborador.getContacto());
  }
}
