package ar.edu.utn.frba.dds.broker;

public interface IClienteMqtt {

  void suscribirPara(ISuscriptorMqtt suscriptor);

  void desuscribirPara(ISuscriptorMqtt suscriptor);

  void publicarMensaje(String topic, String payload);
}
