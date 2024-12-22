package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.broker.ClienteMqtt;
import ar.edu.utn.frba.dds.broker.IClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorSensor;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import java.util.HashSet;
import java.util.Set;

public class SuscriptorSensorService {

  private final IClienteMqtt clienteMqtt;
  private final Set<SuscriptorSensor> suscriptores;
  private final IBrokerMessageHandler brokerMessageHandler;

  public SuscriptorSensorService(IBrokerMessageHandler brokerMessageHandler) {
    clienteMqtt = new ClienteMqtt();
    suscriptores = new HashSet<>();
    this.brokerMessageHandler = brokerMessageHandler;
  }

  public void suscibirPara(Heladera heladera) {
    if (!estaEnElSet(heladera)) {
      suscriptores.add(new SuscriptorSensor(brokerMessageHandler, clienteMqtt, heladera.getId().toString(), heladera.getId()));
    }
    
    suscriptores.stream()
        .filter(suscriptor -> suscriptor.getHeladeraId().equals(heladera.getId()))
        .forEach(SuscriptorSensor::suscribir);
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
