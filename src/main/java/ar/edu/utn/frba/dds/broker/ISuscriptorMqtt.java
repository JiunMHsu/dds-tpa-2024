package ar.edu.utn.frba.dds.broker;

/**
 * Interfaz de Suscriptor MQTT.
 */
public interface ISuscriptorMqtt {
  /**
   * Devuelve el topico al que se suscribe el suscriptor.
   *
   * @return topico al que se suscribe el suscriptor.
   */
  String topic();

  /**
   * Handler de mensajes recibidos.
   *
   * @param mensaje mensaje recibido.
   */
  void recibirMensaje(String mensaje);
}
