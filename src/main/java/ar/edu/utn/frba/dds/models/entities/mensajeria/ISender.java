package ar.edu.utn.frba.dds.models.entities.mensajeria;

import jakarta.mail.MessagingException;

public interface ISender {
  void enviarMensaje(Mensaje mensaje) throws MessagingException;
}
