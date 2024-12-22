package ar.edu.utn.frba.dds.broker;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;

public class SuscriptorSensor implements ISuscriptorMqtt {

  private final ClienteMqtt clienteMqtt;

  private final String _topic;

  @Getter
  private final UUID heladeraId;

  private ScheduledExecutorService scheduler;

  public SuscriptorSensor(ClienteMqtt clienteMqtt, String topic, UUID heladeraId) {
    this.clienteMqtt = clienteMqtt;
    this._topic = topic;
    this.heladeraId = heladeraId;

    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(this::manejarRetrasoMensaje, 5, TimeUnit.MINUTES);

    clienteMqtt.suscribirPara(this);
  }

  @Override
  public String topic() {
    return _topic;
  }

  @Override
  public void recibirMensaje(String mensaje) {
    // TODO ...
  }

  public void manejarRetrasoMensaje() {
    desuscribir();
    // TODO ...
  }

  public void desuscribir() {
    killScheduler();
    clienteMqtt.desuscribirPara(this);
  }

  public void resuscribir() {
    clienteMqtt.suscribirPara(this);

    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(this::manejarRetrasoMensaje, 5, TimeUnit.MINUTES);
  }

  private void killScheduler() {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
    }
  }
}
