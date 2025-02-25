package ar.edu.utn.frba.dds.broker;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.BrokerMessageHandler;
import ar.edu.utn.frba.dds.models.stateless.TipoSensor;
import ar.edu.utn.frba.dds.utils.AppProperties;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import ar.edu.utn.frba.dds.utils.SchedulerManager;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
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

  private ScheduledFuture<?> futureTask;

  private boolean isSubscribed;

  /**
   * Constructor.
   *
   * @param clienteMqtt Cliente MQTT
   * @param topic       Tópico
   * @param heladeraId  Id de la heladera
   */
  public SuscriptorSensor(IClienteMqtt clienteMqtt,
                          String topic,
                          UUID heladeraId) {

    this.clienteMqtt = clienteMqtt;
    this.topic = topic;
    this.heladeraId = heladeraId;
    this.futureTask = null;
    this.brokerMessageHandler = ServiceLocator.instanceOf(BrokerMessageHandler.class);
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

    scheduleMessageDelayHandler();
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
    scheduleMessageDelayHandler();
  }

  /**
   * Desuscribe del broker.
   */
  public void desuscribir() {
    if (!isSubscribed) {
      return;
    }

    cancelMessageDelayHandle();
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

  private void scheduleMessageDelayHandler() {
    this.cancelMessageDelayHandle();

    futureTask = SchedulerManager.scheduleTask(
        this::manejarRetrasoMensaje,
        AppProperties.getInstance().intPropertyFromName("INTERVALO_MENSAJE"),
        TimeUnit.SECONDS);
  }

  private void cancelMessageDelayHandle() {
    if (futureTask != null && !futureTask.isDone()) {
      futureTask.cancel(true);
    }
  }
}
