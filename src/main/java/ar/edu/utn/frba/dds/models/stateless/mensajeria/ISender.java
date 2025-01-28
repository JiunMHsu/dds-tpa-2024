package ar.edu.utn.frba.dds.models.stateless.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import jakarta.mail.MessagingException;

/**
 * Interface for sending messages.
 */
public interface ISender {
  void enviarMensaje(Mensaje mensaje) throws MessagingException;
}
