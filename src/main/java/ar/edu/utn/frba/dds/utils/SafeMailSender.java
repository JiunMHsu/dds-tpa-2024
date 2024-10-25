package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.mensajeria.ISender;
import jakarta.mail.MessagingException;

public class SafeMailSender implements ISender {
    @Override
    public void enviarMensaje(Contacto contacto, String asunto, String cuerpo) throws MessagingException {
        System.out.println("Sending massage to " + contacto.getEmail());
    }
}
