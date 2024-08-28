package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import ar.edu.utn.frba.dds.broker.suscriptorSensor.SuscriptorSensorTemperatura;
import ar.edu.utn.frba.dds.models.heladera.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBroker {

  private ClienteMqtt cliente;
  private final String topic = "dds/g22/test";

  @BeforeEach
  public void setUp() {
    cliente = new ClienteMqtt();
  }

  @Test
  @DisplayName("El Suscriptor puede recibir correctamente los mensajes publicados en el topic específico")
  public void testSuscripcion() throws InterruptedException {
    class SuscriptorTest implements SuscriptorMqtt {
      @Override
      public String topic() {
        return topic;
      }

      @Override
      public void recibirMensaje(String mensaje) {
        System.out.println(mensaje);
      }
    }

    SuscriptorMqtt unSuscriptor = new SuscriptorTest();
    cliente.suscribirPara(unSuscriptor);
    ClienteMqtt clientePublicador = new ClienteMqtt();

    for (int i = 0; i < 5; i++) {
      clientePublicador.publicarMensaje(topic, "dds-test");
      Thread.sleep(500);
    }
  }

  @Test
  public void mensajeFueraDeTiempo() throws InterruptedException {
    SuscriptorSensorTemperatura unSuscriptor = SuscriptorSensorTemperatura.para(Sensor.de(topic), 3000, 1);
    ClienteMqtt clientePublicador = new ClienteMqtt();
    cliente.suscribirPara(unSuscriptor);
    clientePublicador.publicarMensaje(topic, "dds-test");

    Thread.sleep(5000);
  }
}
