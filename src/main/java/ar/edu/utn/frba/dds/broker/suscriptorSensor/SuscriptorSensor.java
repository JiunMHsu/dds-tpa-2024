package ar.edu.utn.frba.dds.broker.suscriptorSensor;

import ar.edu.utn.frba.dds.broker.SuscriptorMqtt;
import ar.edu.utn.frba.dds.models.entities.sensor.Sensor;
import lombok.Getter;

@Getter
public abstract class SuscriptorSensor implements SuscriptorMqtt {
    private final Sensor suscriptorSensor;

    protected SuscriptorSensor(Sensor suscriptor) {
        this.suscriptorSensor = suscriptor;
    }

    public String topic() {
        return this.suscriptorSensor.getTopic();
    }

    public abstract void recibirMensaje(String mensaje);
}
