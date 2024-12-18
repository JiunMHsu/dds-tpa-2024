package ar.edu.utn.frba.dds.broker.suscriptorSensor;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.entities.sensor.Sensor;

public class SuscriptorLectorTarjeta extends SuscriptorSensor {

  public SuscriptorLectorTarjeta(Sensor suscriptor) {
    super(suscriptor);
  }

  @Override
  public void recibirMensaje(String mensaje) {
    ServiceLocator.instanceOf(HeladeraController.class).recibirCodigoTarjeta(mensaje, getSuscriptorSensor().getHeladera());
  }

}
