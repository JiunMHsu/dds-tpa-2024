package ar.edu.utn.frba.dds.models.sensor;

import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tarjeta.ExcepcionUsoInvalido;
import ar.edu.utn.frba.dds.models.tarjeta.ITarjeta;
import ar.edu.utn.frba.dds.repository.tarjeta.TarjetaRepository;

public class LectorTarjeta implements SuscriptorMqtt {

  private final Heladera heladera;
  // TODO
  // hay que armar el publisher para la respuesta

  public LectorTarjeta(Heladera heladera) {
    super();
    this.heladera = heladera;
    this.suscribir(heladera.getTopicLectorTarjeta());
  }

  /**
   * Para este caso, el mensaje será el código de la tarjeta.
   */
  @Override
  public void recibirMensaje(String mensaje) {
    ITarjeta tarjeta = TarjetaRepository.obtenerPorCodigo(mensaje);

    try {
      tarjeta.registrarUso(heladera);
      this.responderSolicitud("AUTHORIZED");
    } catch (ExcepcionUsoInvalido e) {
      this.responderSolicitud("UNAUTHORIZED");
    }
  }

  // TODO
  private void responderSolicitud(String mensaje) {
    System.out.println(mensaje);
  }
}
