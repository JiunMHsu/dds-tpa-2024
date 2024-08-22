package ar.edu.utn.frba.dds.mensajeria;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TelegramSender implements Sender {

  private final String AUTHORIZATION_TOKEN;
  private final String CHAT_ID;
  private CamelContext camelContext;

  public TelegramSender(String authorizationToken, String chatID) {
    this.AUTHORIZATION_TOKEN = authorizationToken;
    this.CHAT_ID = chatID;
    this.camelContext = new DefaultCamelContext();

    try {
      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() {
          from("direct:sendMessage")
                  .toF("telegram:bots/?authorizationToken=%s&chatId=%s", AUTHORIZATION_TOKEN, CHAT_ID);
        }
      });
      camelContext.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enviarMensaje(String receptor, String asunto, String cuerpo) {
    ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
    try {
      producerTemplate.sendBody("direct:sendMessage", asunto + "\n" + cuerpo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stop() {
    try {
      camelContext.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
