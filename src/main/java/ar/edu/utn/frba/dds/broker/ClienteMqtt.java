package ar.edu.utn.frba.dds.broker;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Cliente Mqtt.
 */
public class ClienteMqtt {

  private final Mqtt5BlockingClient client;

  /**
   * Constructor de un cliente Mqtt.
   * La instanciación implica también la conexión al broker
   * y suscripción al tópico especificado por el suscriptor.
   */
  public ClienteMqtt() {
    client = Mqtt5Client
        .builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost("broker.hivemq.com")
        .serverPort(1883)
        .buildBlocking();

    int minuto = 60;
    client.connectWith()
        .keepAlive(5 * minuto)
        .send();
  }

  public void suscribirPara(SuscriptorMqtt suscriptor) {
    client.toAsync().subscribeWith()
        .topicFilter(suscriptor.topic())
        .qos(MqttQos.AT_MOST_ONCE)
        .callback(mqtt5Publish -> suscriptor.recibirMensaje(
            StandardCharsets.UTF_8.decode(mqtt5Publish.getPayload().get()).toString())
        )
        .send();
  }

  public void publicarMensaje(String topic, String payload) {
    client.publishWith()
        .topic(topic)
        .payload(payload.getBytes(StandardCharsets.UTF_8))
        .qos(MqttQos.AT_MOST_ONCE)
        .send();
  }
}
