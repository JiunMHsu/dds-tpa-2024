package ar.edu.utn.frba.dds.broker.suscriptorSensor;

import ar.edu.utn.frba.dds.models.heladera.Sensor;

public class SuscriptorSensorMovimiento extends SuscriptorSensor {

  public SuscriptorSensorMovimiento(Sensor suscriptor) {
    super(suscriptor);
  }

  // TODO (delegar a controller? o este mismo cumple el rol de controller?)
  @Override
  public void recibirMensaje(String mensaje) {
  }

}
