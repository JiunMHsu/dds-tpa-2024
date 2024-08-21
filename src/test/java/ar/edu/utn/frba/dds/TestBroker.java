package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBroker {

  SuscriptorMqtt unSuscriptor;
  ClienteMqtt cliente;

  @BeforeEach
  public void setUp() {

    class SuscriptorTest implements SuscriptorMqtt {
      @Override
      public String topic() {
        return "dds/g22/test";
      }

      @Override
      public void recibirMensaje(String mensaje) {
        System.out.println(mensaje);
      }
    }

    unSuscriptor = new SuscriptorTest();
  }

  @Test
  @DisplayName("")
  public void testSuscripcion() {
    cliente = new ClienteMqtt(unSuscriptor);
  }
}
