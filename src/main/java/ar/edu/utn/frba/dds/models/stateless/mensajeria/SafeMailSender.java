package ar.edu.utn.frba.dds.models.stateless.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import jakarta.mail.MessagingException;

/**
 * SafeMailSender class that implements ISender.
 */
public class SafeMailSender implements ISender {
  @Override
  public void enviarMensaje(Mensaje mensaje) throws MessagingException {
    System.out.println("Sending massage to " + mensaje.getContacto().getValor());
  }
}
