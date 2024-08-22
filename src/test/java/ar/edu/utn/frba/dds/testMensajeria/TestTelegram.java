package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.TelegramSender;
import org.junit.jupiter.api.Test;

public class TestTelegram {
    @Test
    public void enviarTelegram() {
        TelegramSender telegramSender = new TelegramSender(
                "7528108332:AAH5sYAJW-xnd0EVQqaPAXaN1DSY7A6qAkE",
                "");

        telegramSender.enviarMensaje("", "test", "Test - Grupo 22");
    }
}

