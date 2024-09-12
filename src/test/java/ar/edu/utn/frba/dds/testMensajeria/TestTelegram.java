package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.TelegramSender;
import org.junit.jupiter.api.Test;

public class TestTelegram {

  @Test
  public void enviarTelegram() {
    TelegramSender telegramSender = new TelegramSender();

    telegramSender.enviarMensaje("", "**** TEST DE TELEGRAM ****", "Si te llego este mensaje significa que el test funciono =D");
  }
}

