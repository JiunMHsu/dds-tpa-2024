package ar.edu.utn.frba.dds.broker;

import ar.edu.utn.frba.dds.utils.AppProperties;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ClienteMqtt {

  private final Mqtt5BlockingClient client;

  public ClienteMqtt() {
    client = Mqtt5Client
        .builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost(AppProperties.getInstance().propertyFromName("BROKER_HOST"))
        .serverPort(AppProperties.getInstance().intPropertyFromName("BROKER_PORT"))
        .buildBlocking();

    client.connectWith().send();
  }

  public void suscribirPara(ISuscriptorMqtt suscriptor) {
    client.toAsync().subscribeWith()
        .topicFilter(suscriptor.topic())
        .qos(MqttQos.AT_MOST_ONCE)
        .callback(mqtt5Publish -> {
          String mensaje = "";
          if (mqtt5Publish.getPayload().isPresent()) {
            mensaje = StandardCharsets.UTF_8.decode(mqtt5Publish.getPayload().get()).toString();
          }
          suscriptor.recibirMensaje(mensaje);
        })
        .send()
        .whenComplete((subAck, throwable) -> {
          if (throwable != null) {
            throwable.printStackTrace();
            System.out.printf("La suscripción a %s falló. \n", suscriptor.topic());
          } else {
            System.out.printf("La suscripción a %s completada. \n", suscriptor.topic());
          }
        });
  }

  public void publicarMensaje(String topic, String payload) {
    client.publishWith()
        .topic(topic)
        .retain(true)
        .payload(payload.getBytes(StandardCharsets.UTF_8))
        .qos(MqttQos.AT_LEAST_ONCE)
        .send();
  }
}
