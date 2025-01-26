package ar.edu.utn.frba.dds.models.entities.mensajeria;

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
