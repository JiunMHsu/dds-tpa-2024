package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.WhatsAppSender;
import ar.edu.utn.frba.dds.models.data.Contacto;
import org.junit.jupiter.api.Test;

public class TestWhatsApp {
    @Test
    public void enviarWhatsApp() {
        WhatsAppSender whatsAppSender = new WhatsAppSender();
        whatsAppSender.enviarMensaje(Contacto.conWhatsApp("541160499220"), "test", "Test - Grupo 22");
    }
}
