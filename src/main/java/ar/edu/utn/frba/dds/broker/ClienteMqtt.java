package ar.edu.utn.frba.dds.broker;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import java.util.UUID;

public abstract class ClienteMqtt {

  Mqtt5BlockingClient client;

  public ClienteMqtt() {
    client = Mqtt5Client
        .builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost("broker.hivemq.com")
        .serverPort(1883)
        .buildBlocking();

    client.connect();
  }

  public void suscribir(String topic) {
    client.toAsync().subscribeWith()
        .topicFilter(topic)
        .qos(MqttQos.AT_MOST_ONCE)
        .callback(mqtt5Publish -> this.recibirMensaje(mqtt5Publish.toString()))
        .send();
  }

  public abstract void recibirMensaje(String mensaje);
}
