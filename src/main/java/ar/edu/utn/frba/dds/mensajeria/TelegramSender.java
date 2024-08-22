package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.AppConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TelegramSender implements Sender {
  private final String botToken;
  private String chatId; // Inicialmente null
  private final CamelContext camelContext;
  private final ProducerTemplate producerTemplate;

  public TelegramSender() {
    this.botToken = AppConfig.getProperty("TELEGRAM_AUTHORIZATION_TOKEN");
    this.camelContext = new DefaultCamelContext();
    this.producerTemplate = camelContext.createProducerTemplate();
    configurarCamelContext();
  }

  private void configurarCamelContext() {
    try {
      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() {
          // Ruta para obtener el chatId
          from("telegram:bots?authorizationToken=" + botToken)
                  .to("log:INFO?showHeaders=true")
                  .process(exchange -> {
                    // Obtener el chatId de los headers
                    String receivedChatId = exchange.getIn().getHeader("CamelTelegramChatId", String.class);
                    if (receivedChatId != null) {
                      chatId = receivedChatId;
                      System.out.println("Chat ID obtenido: " + chatId);
                    }
                  });

          // Ruta para enviar mensajes, utiliza el chatId cuando está disponible
          from("direct:sendToTelegram")
                  .choice()
                  .when(exchange -> chatId != null)
                  .to("telegram:bots?authorizationToken=" + botToken + "&chatId=" + chatId)
                  .otherwise()
                  .log("Chat ID no disponible. No se puede enviar el mensaje.");
        }
      });
      camelContext.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void obtenerChatId() {
    try {
      producerTemplate.sendBody("telegram:bots?authorizationToken=" + botToken, "Obteniendo Chat ID");
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
}

