package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.data.Contacto;
import org.junit.jupiter.api.Test;

public class TestTelegram {

    @Test
    public void enviarTelegram() {
        TelegramSender telegramSender = new TelegramSender();

        telegramSender.enviarMensaje(
                Contacto.conTelegram(""),
                "**** TEST DE TELEGRAM ****",
                "Si te llego este mensaje significa que el test funciono =D"
        );
    }
}

