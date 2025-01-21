package ar.edu.utn.frba.dds.models.entities.mensajeria;

import jakarta.mail.MessagingException;
import java.time.LocalDateTime;

public class Notificador {

  private final ISender sender;

  public Notificador(ISender sender) {
    this.sender = sender;
  }
  public void enviarNotificacion(Mensaje mensaje) throws MessagingException {
    sender.enviarMensaje(mensaje);
    mensaje.setFechaEnvio(LocalDateTime.now());
  }
}
