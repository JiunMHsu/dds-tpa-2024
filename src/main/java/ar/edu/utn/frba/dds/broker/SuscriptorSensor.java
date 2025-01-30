package ar.edu.utn.frba.dds.broker;

import ar.edu.utn.frba.dds.models.stateless.TipoSensor;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;

/**
 * Suscriptor de sensor.
 */
public class SuscriptorSensor implements ISuscriptorMqtt {

  private final IClienteMqtt clienteMqtt;

  private final String topic;

  @Getter
  private final UUID heladeraId;

  private final IBrokerMessageHandler brokerMessageHandler;

  private ScheduledExecutorService scheduler;

  private boolean isSubscribed;

  /**
   * Constructor.
   *
   * @param brokerMessageHandler Handler de mensajes del broker
   * @param clienteMqtt          Cliente MQTT
   * @param topic                Tópico
   * @param heladeraId           ID de la heladera
   */
  public SuscriptorSensor(IBrokerMessageHandler brokerMessageHandler,
                          IClienteMqtt clienteMqtt,
                          String topic,
                          UUID heladeraId) {

    this.clienteMqtt = clienteMqtt;
    this.topic = topic;
    this.heladeraId = heladeraId;
    this.scheduler = null;
    this.brokerMessageHandler = brokerMessageHandler;
    this.isSubscribed = false;
  }

  @Override
  public String topic() {
    return this.topic;
  }

  @Override
  public void recibirMensaje(String mensaje) {
    System.out.println("Mensaje recibido por " + this.topic() + ": " + mensaje);

    String[] vectorMensaje = mensaje.split(" ");

    switch (parseOperation(vectorMensaje[0])) {
      case TEMPERATURA ->
          brokerMessageHandler.manejarTemperatura(Double.parseDouble(vectorMensaje[1]), heladeraId);
      case FRAUDE -> brokerMessageHandler.manejarFraude(heladeraId);
      case LECTOR_TARJETA ->
          brokerMessageHandler.manejarSolicitudDeApertura(vectorMensaje[1], heladeraId);
      default ->
          throw new IllegalStateException("Unexpected value: " + parseOperation(vectorMensaje[0]));
    }

    newScheduler();
  }

  /**
   * Suscribe al broker.
   */
  public void suscribir() {
    if (isSubscribed) {
      return;
    }

    clienteMqtt.suscribirPara(this);
    isSubscribed = true;
    newScheduler();
  }

  /**
   * Desuscribe del broker.
   */
  public void desuscribir() {
    if (!isSubscribed) {
      return;
    }

    killScheduler();
    isSubscribed = false;
    clienteMqtt.desuscribirPara(this);
  }

  private void manejarRetrasoMensaje() {
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
      scheduler.shutdownNow();
      scheduler = null;
    }
  }

  private void newScheduler() {
    killScheduler();
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(this::manejarRetrasoMensaje, 30, TimeUnit.SECONDS);
  }

}
