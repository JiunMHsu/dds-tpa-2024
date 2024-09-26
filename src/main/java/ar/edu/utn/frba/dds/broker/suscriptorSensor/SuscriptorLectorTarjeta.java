package ar.edu.utn.frba.dds.broker.suscriptorSensor;

import ar.edu.utn.frba.dds.models.entities.sensor.Sensor;

public class SuscriptorLectorTarjeta extends SuscriptorSensor {

    public SuscriptorLectorTarjeta(Sensor suscriptor) {
        super(suscriptor);
    }

    // TODO (delegar a controller? o este mismo cumple el rol de controller?)
    @Override
    public void recibirMensaje(String mensaje) {
    }

}
