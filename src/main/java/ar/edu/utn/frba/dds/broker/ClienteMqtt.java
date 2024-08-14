package ar.edu.utn.frba.dds.broker;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import java.util.UUID;

/**
 * Cliente Mqtt.
 */
public class ClienteMqtt {

  Mqtt5BlockingClient client;
  SuscriptorMqtt suscriptor;

  /**
   * Constructor de un cliente Mqtt.
   * La instanciación implica también la conexión al broker
   * y suscripción al tópico especificado por el suscriptor.
   *
   * @param suscriptor Un suscriptor (cliente concreto).
   */
  public ClienteMqtt(SuscriptorMqtt suscriptor) {
    this.suscriptor = suscriptor;

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

    client.toAsync().subscribeWith()
        .topicFilter(suscriptor.topic())
        .qos(MqttQos.AT_MOST_ONCE)
        .callback(mqtt5Publish -> suscriptor.recibirMensaje(mqtt5Publish.toString()))
        .send();
  }

}
