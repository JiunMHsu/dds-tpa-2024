package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.TelegramSender;
import org.junit.jupiter.api.Test;

public class TelegramTest {

  @Test
  public void enviarTelegram() {
    TelegramSender telegramSender = new TelegramSender();

    Mensaje mensaje = Mensaje.con(Contacto.conTelegram(""), "**** TEST DE TELEGRAM ****", "Si te llego este mensaje significa que el test funciono =D");
    telegramSender.enviarMensaje(mensaje);
  }
}

