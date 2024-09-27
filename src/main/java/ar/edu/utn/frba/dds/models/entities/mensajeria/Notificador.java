package ar.edu.utn.frba.dds.models.entities.mensajeria;

import java.time.LocalDateTime;

public class Notificador {

    private ISender sender;

    public Notificador(ISender sender) {
        this.sender = sender;
    }

    public void enviarNotificacion(Mensaje mensaje) {
        sender.enviarMensaje(mensaje.getReceptor().getContacto(), mensaje.getAsunto(), mensaje.getCuerpo());
        mensaje.setFechaEnvio(LocalDateTime.now());
    }
}
