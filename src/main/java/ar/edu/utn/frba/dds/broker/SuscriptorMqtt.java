package ar.edu.utn.frba.dds.broker;

public interface SuscriptorMqtt {
  void recibirMensaje(String mensaje);

  String getTopic();
}
