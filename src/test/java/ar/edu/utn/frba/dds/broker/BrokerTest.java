package ar.edu.utn.frba.dds.broker;

import ar.edu.utn.frba.dds.broker.suscriptorSensor.SuscriptorSensorTemperatura;
import ar.edu.utn.frba.dds.models.entities.sensor.Sensor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrokerTest {

    @Test
    @DisplayName("El Suscriptor puede recibir correctamente los mensajes publicados en el topic específico")
    public void testSuscripcion() throws InterruptedException {
        final String topic = "dds/g22/test/1";
        final ClienteMqtt cliente = new ClienteMqtt();

        class SuscriptorTest implements ISuscriptorMqtt {
            @Override
            public String topic() {
                return topic;
            }

            @Override
            public void recibirMensaje(String mensaje) {
                System.out.println(mensaje);
            }
        }

        ISuscriptorMqtt unSuscriptor = new SuscriptorTest();
        cliente.suscribirPara(unSuscriptor);
        ClienteMqtt clientePublicador = new ClienteMqtt();

        for (int i = 0; i < 5; i++) {
            clientePublicador.publicarMensaje(topic, "dds-test-1");
            Thread.sleep(500);
        }
    }

    @Test
    @DisplayName("El Suscriptor puede manejar escenarios donde los mensajes no llegan en el tiempo esperado.")
    public void mensajeFueraDeTiempo() throws InterruptedException {
        final String topic = "dds/g22/test/2";
        final ClienteMqtt cliente = new ClienteMqtt();

        SuscriptorSensorTemperatura unSuscriptor = SuscriptorSensorTemperatura.para(Sensor.de(topic), 3000, 1);
        ClienteMqtt clientePublicador = new ClienteMqtt();
        cliente.suscribirPara(unSuscriptor);
        clientePublicador.publicarMensaje(topic, "dds-test temp");

        Thread.sleep(5000);
    }
}
