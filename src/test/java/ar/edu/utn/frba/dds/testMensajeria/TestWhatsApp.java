package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.WhatsAppSender;
import org.junit.jupiter.api.Test;

public class TestWhatsApp {
    @Test
    public void enviarWhatsApp() {
        WhatsAppSender whatsAppSender = new WhatsAppSender(
                "394679593732404",
                "EAAKQbkh4qmYBO3LIZAXW6CGxsgrJyutZA2WDeZBO375LZClhZA6MpME2q5AcmHSQnPbUxrbWl6WQomkcSF3ECOyffr2vZBHYZBtkEZAukvIXNOksIHRtEu7vjQdoImIBaBYbFJnXChpBRK5nuHiqUXbPxX5tJ2QaylQZAFZBjFCBVow5xBSxWgb1p8noDyWlLuk1jsYkNGeDsHI0Tw36niGycZD");

        whatsAppSender.enviarMensaje("541142420699", "test", "Test - Grupo 22");
    }
}
