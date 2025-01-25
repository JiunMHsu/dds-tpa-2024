package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.mensajeria.ISender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import jakarta.mail.MessagingException;

public class SafeMailSender implements ISender {
  @Override
  public void enviarMensaje(Mensaje mensaje) throws MessagingException {
    System.out.println("Sending massage to " + mensaje.getContacto().getValor());
  }
}
