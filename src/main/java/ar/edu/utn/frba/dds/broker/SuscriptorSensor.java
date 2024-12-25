package ar.edu.utn.frba.dds.broker;

import ar.edu.utn.frba.dds.models.stateless.TipoSensor;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;

public class SuscriptorSensor implements ISuscriptorMqtt {

  private final IClienteMqtt clienteMqtt;

  private final String _topic;

  @Getter
  private final UUID heladeraId;

  private final IBrokerMessageHandler brokerMessageHandler;

  private ScheduledExecutorService scheduler;

  public SuscriptorSensor(IBrokerMessageHandler brokerMessageHandler, IClienteMqtt clienteMqtt, String topic, UUID heladeraId) {
    this.clienteMqtt = clienteMqtt;
    this._topic = topic;
    this.heladeraId = heladeraId;
    this.scheduler = null;
    this.brokerMessageHandler = brokerMessageHandler;
  }

  @Override
  public String topic() {
    return _topic;
  }

  @Override
  public void recibirMensaje(String mensaje) {
    // TODO: Log message
    System.out.println("Mensaje recibido por " + this.topic() + ": " + mensaje);

    String[] vectorMensaje = mensaje.split(" ");

    switch (parseOperation(vectorMensaje[0])) {
      case TEMPERATURA ->
          brokerMessageHandler.manejarTemperatura(Double.parseDouble(vectorMensaje[1]), heladeraId);
      case FRAUDE -> brokerMessageHandler.manejarFraude(heladeraId);
      case LECTOR_TARJETA ->
          brokerMessageHandler.manejarSolicitudDeApertura(vectorMensaje[1], heladeraId);
    }

    newScheduler();
  }

  public void suscribir() {
    clienteMqtt.suscribirPara(this);
    newScheduler();
  }

  public void desuscribir() {
    killScheduler();
    clienteMqtt.desuscribirPara(this);
  }

  private void manejarRetrasoMensaje() {
    // TODO: Log message

    desuscribir();
    brokerMessageHandler.manejarFallaConexion(heladeraId);
  }

  private TipoSensor parseOperation(String mensaje) {
    return switch (mensaje) {
      case "TEMPERATURA" -> TipoSensor.TEMPERATURA;
      case "FRAUDE" -> TipoSensor.FRAUDE;
      case "SOLICITUD_APERTURA" -> TipoSensor.LECTOR_TARJETA;
      default -> throw new IllegalStateException("Unexpected value: " + mensaje);
    };
  }

  private void killScheduler() {
    if (scheduler != null && !scheduler.isShutdown()) {
      scheduler.shutdown();
      scheduler = null;
    }
  }

  private void newScheduler() {
    killScheduler();
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(this::manejarRetrasoMensaje, 5, TimeUnit.MINUTES);
  }

}
