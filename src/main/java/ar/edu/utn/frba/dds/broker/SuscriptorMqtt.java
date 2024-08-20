package ar.edu.utn.frba.dds.broker;

public interface SuscriptorMqtt {
  String topic();

  void recibirMensaje(String mensaje);
}
