package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.EmailSender;
import ar.edu.utn.frba.dds.models.data.Contacto;
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
