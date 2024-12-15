    package ar.edu.utn.frba.dds.models.entities.mensajeria;

    import ar.edu.utn.frba.dds.models.entities.data.Contacto;
    import jakarta.mail.MessagingException;

    public interface ISender {
        void enviarMensaje(Contacto contacto, String asunto, String cuerpo) throws MessagingException;
    }
