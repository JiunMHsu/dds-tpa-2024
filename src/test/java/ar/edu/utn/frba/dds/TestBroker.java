package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBroker {

  private SuscriptorMqtt unSuscriptor;
  private ClienteMqtt cliente;
  private final String topic = "dds/g22/test";

  @BeforeEach
  public void setUp() {

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

    unSuscriptor = new SuscriptorTest();
    cliente = new ClienteMqtt();
  }

  @Test
  @DisplayName("El Suscriptor puede recibir correctamente los mensajes publicados en el topic específico")
  public void testSuscripcion() throws InterruptedException {
    cliente.suscribirPara(unSuscriptor);
    ClienteMqtt clientePublicador = new ClienteMqtt();

    for (int i = 0; i < 5; i++) {
      clientePublicador.publicarMensaje(topic, "dds-test");
      Thread.sleep(500);
    }
  }

}
