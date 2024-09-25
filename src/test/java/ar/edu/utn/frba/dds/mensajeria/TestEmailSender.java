package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import org.junit.jupiter.api.Test;

public class TestEmailSender {

    @Test
    public void testEmailSender() {
        EmailSender mailSender = new EmailSender();
        mailSender.enviarMensaje(
                Contacto.conEmail("jhsu@frba.utn.edu.ar"),
                "TEST DDS EMAIL SENDER",
                "test"
        );
    }

}
