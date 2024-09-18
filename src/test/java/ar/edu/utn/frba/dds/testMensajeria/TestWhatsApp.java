package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.WhatsAppSender;
import org.junit.jupiter.api.Test;

public class TestWhatsApp {
    @Test
    public void enviarWhatsApp() {
        WhatsAppSender whatsAppSender = new WhatsAppSender();
        whatsAppSender.enviarMensaje("541160499220", "test", "Test - Grupo 22");
    }
}
