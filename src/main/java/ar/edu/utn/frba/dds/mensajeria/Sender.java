package ar.edu.utn.frba.dds.mensajeria;

public interface Sender {
  void enviarMensaje(String receptor, String asunto, String cuerpo);
}
