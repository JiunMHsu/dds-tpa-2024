package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorSensor;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import java.util.HashSet;
import java.util.Set;

public class SuscriptorSensorService {

  private final ClienteMqtt clienteMqtt;
  private final Set<SuscriptorSensor> suscriptores;

  public SuscriptorSensorService() {
    clienteMqtt = new ClienteMqtt();
    suscriptores = new HashSet<>();
  }

  public void suscibirPara(Heladera heladera) {
    if (!estaEnElSet(heladera)) {
      suscriptores.add(new SuscriptorSensor(clienteMqtt, heladera.getId().toString(), heladera.getId()));
    }
    suscriptores.stream()
        .filter(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()))
        .forEach(SuscriptorSensor::resuscribir);
  }

  public void desuscribirPara(Heladera heladera) {
    suscriptores.stream()
        .filter(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()))
        .forEach(SuscriptorSensor::desuscribir);

    suscriptores.removeIf(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()));
  }

  private boolean estaEnElSet(Heladera heladera) {
    return suscriptores.stream()
        .anyMatch(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()));
  }

}
