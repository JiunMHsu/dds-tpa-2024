package ar.edu.utn.frba.dds.broker;

/**
 * Interfaz de Cliente MQTT.
 */
//TODO check mayuscula
public interface IClienteMqtt {

  /**
   * Conecta el cliente al broker MQTT.
   */
  void suscribirPara(ISuscriptorMqtt suscriptor);

  /**
   * Desconecta el cliente del broker MQTT.
   */
  void desuscribirPara(ISuscriptorMqtt suscriptor);

  /**
   * Publica un mensaje en un tópico.
   * TODO: Revisar el fujo que lo debería usar.
   *
   * @param topic   Tópico en el que se publicará el mensaje.
   * @param payload Mensaje a publicar.
   */
  void publicarMensaje(String topic, String payload);
}
