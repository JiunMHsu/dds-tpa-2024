package ar.edu.utn.frba.dds.broker.suscriptorSensor;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import ar.edu.utn.frba.dds.models.heladera.Sensor;
import lombok.Getter;

@Getter
public abstract class SuscriptorSensor implements SuscriptorMqtt {
  private final ClienteMqtt clienteMqtt;
  private final Sensor suscriptorSensor;

  protected SuscriptorSensor(ClienteMqtt cliente, Sensor suscriptor) {
    this.clienteMqtt = cliente;
    this.suscriptorSensor = suscriptor;
  }

  public String topic() {
    return this.suscriptorSensor.getTopic();
  }

  public abstract void recibirMensaje(String mensaje);
}
