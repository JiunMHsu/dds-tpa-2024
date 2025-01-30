package ar.edu.utn.frba.dds.broker;

/**
 * Interfaz de Cliente MQTT.
 */
public interface IClienteMqtt {

  void suscribirPara(ISuscriptorMqtt suscriptor);

  void desuscribirPara(ISuscriptorMqtt suscriptor);

  void publicarMensaje(String topic, String payload);
}
