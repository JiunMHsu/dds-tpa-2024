package ar.edu.utn.frba.dds.mensajeria;

import java.time.LocalDateTime;

public class Notificador {

    private Sender sender;

    public Notificador(Sender sender) {
        this.sender = sender;
    }

    public void enviarNotificacion(Mensaje mensaje) {
        sender.enviarMensaje(mensaje.getReceptor().getContacto(), mensaje.getAsunto(), mensaje.getCuerpo());
        mensaje.setFechaEnvio(LocalDateTime.now());
    }
}
