package ar.edu.utn.frba.dds.broker;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClienteMqtt {

  private final Mqtt5BlockingClient client;
  private final Set<String> topicsSuscritos;

  public ClienteMqtt() {
    topicsSuscritos = new HashSet<>();
    client = Mqtt5Client
        .builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost("broker.hivemq.com")
        .serverPort(1883)
        .buildBlocking();

    client.connectWith().send();
  }

  public void suscribirPara(ISuscriptorMqtt suscriptor) {
    if (topicsSuscritos.contains(suscriptor.topic())) {
      return;
    }

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
            topicsSuscritos.add(suscriptor.topic());
          }
        });
  }

  public void desuscribirPara(ISuscriptorMqtt suscriptor) {
    if (!topicsSuscritos.contains(suscriptor.topic())) {
      return;
    }

    Mqtt5Unsubscribe unsubscribe = Mqtt5Unsubscribe.builder().topicFilter(suscriptor.topic()).build();
    client.toAsync().unsubscribe(unsubscribe)
        .whenComplete((mqtt5UnsubAck, throwable) -> {
          if (throwable != null) {
            throwable.printStackTrace();
            System.out.printf("La desuscripción de %s falló. \n", suscriptor.topic());
          } else {
            System.out.printf("La desuscripción de %s completada. \n", suscriptor.topic());
            topicsSuscritos.remove(suscriptor.topic());
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
