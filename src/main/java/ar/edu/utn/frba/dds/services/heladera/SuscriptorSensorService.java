package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.broker.IClienteMqtt;
import ar.edu.utn.frba.dds.broker.SuscriptorSensor;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de suscriptor de sensor.
 */
public class SuscriptorSensorService {

  private final HeladeraService heladeraService;
  private final IClienteMqtt clienteMqtt;
  private final Map<String, SuscriptorSensor> suscriptores;

  /**
   * Constructor.
   *
   * @param heladeraService Servicio de heladera
   * @param clienteMqtt     Cliente MQTT
   */
  public SuscriptorSensorService(HeladeraService heladeraService,
                                 IClienteMqtt clienteMqtt) {
    this.heladeraService = heladeraService;
    this.clienteMqtt = clienteMqtt;
    this.suscriptores = new HashMap<>();
  }

  /**
   * Inicializa todos los suscriptores de heladeras.
   */
  public void initialize() {
    heladeraService.buscarTodas()
        .stream()
        .filter(Heladera::estaActiva)
        .forEach(this::suscribirPara);
  }

  /**
   * Suscribe un cliente MQTT a un tópico de una heladera.
   *
   * @param heladera Heladera
   */
  public void suscribirPara(Heladera heladera) {
    SuscriptorSensor suscriptor = suscriptores.getOrDefault(
        heladera.getBrokerTopic(),
        new SuscriptorSensor(
            clienteMqtt,
            heladera.getBrokerTopic(),
            heladera.getId()
        )
    );

    suscriptor.suscribir();
    suscriptores.put(heladera.getBrokerTopic(), suscriptor);
  }

  /**
   * Desuscribe un cliente MQTT de un tópico de una heladera.
   *
   * @param heladera Heladera
   */
  public void desuscribirPara(Heladera heladera) {
    SuscriptorSensor suscriptor = suscriptores.remove(heladera.getBrokerTopic());

    if (suscriptor != null) {
      suscriptor.desuscribir();
    }
  }
}
