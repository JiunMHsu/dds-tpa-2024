package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.TelegramSender;
import org.junit.jupiter.api.Test;

public class TestTelegram {

    @Test
    public void enviarTelegram() {
        TelegramSender telegramSender = new TelegramSender();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        telegramSender.enviarMensaje("", "test", "Test - Grupo 22");
    }
}

