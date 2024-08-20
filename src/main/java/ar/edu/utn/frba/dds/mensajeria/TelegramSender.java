package ar.edu.utn.frba.dds.mensajeria;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

public class TelegramSender implements Sender {

  private static String TELEGRAM_ENDPOINT; // Deberia ser el bot por ahora lo dejo asi

  public void enviarMensaje(String receptor, String asunto, String cuerpo) {

    CamelContext context = new DefaultCamelContext();

    try {
      context.start();
      ProducerTemplate producerTemplate = context.createProducerTemplate();
      producerTemplate.sendBody(TELEGRAM_ENDPOINT, asunto + "\n" + cuerpo);

    } catch (Exception error) {
      error.printStackTrace();

    } finally {
      try {
        context.stop();
      } catch (Exception error) {
        error.printStackTrace();
      }
    }
  }
}
