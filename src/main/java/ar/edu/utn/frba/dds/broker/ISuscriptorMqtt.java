package ar.edu.utn.frba.dds.broker;

public interface ISuscriptorMqtt {
    String topic();

    void recibirMensaje(String mensaje);
}
