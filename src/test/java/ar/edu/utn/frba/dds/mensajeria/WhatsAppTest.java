package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.mensajeria.WhatsAppSender;
import org.junit.jupiter.api.Test;

public class WhatsAppTest {
    @Test
    public void enviarWhatsApp() {
        WhatsAppSender whatsAppSender = new WhatsAppSender();
        whatsAppSender.enviarMensaje(Contacto.conWhatsApp("541160499220"), "test", "Test - Grupo 22");
    }
}
