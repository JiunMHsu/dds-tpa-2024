package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.data.Contacto;

public interface Sender {
    void enviarMensaje(Contacto contacto, String asunto, String cuerpo);
}
