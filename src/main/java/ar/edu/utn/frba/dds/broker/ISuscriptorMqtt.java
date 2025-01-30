package ar.edu.utn.frba.dds.broker;

/**
 * Interfaz de Suscriptor MQTT.
 */
public interface ISuscriptorMqtt {
  String topic();

  void recibirMensaje(String mensaje);
}
