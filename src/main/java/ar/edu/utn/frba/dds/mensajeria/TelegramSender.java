package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.AppConfig;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TelegramSender implements Sender {
  private final String AUTHORIZATION_TOKEN;
  private String CHAT_ID;
  private final CamelContext camelContext;
  private final ProducerTemplate producerTemplate;

  public TelegramSender() {
    this.AUTHORIZATION_TOKEN = AppConfig.getProperty("TELEGRAM_AUTHORIZATION_TOKEN");
    this.camelContext = new DefaultCamelContext();
    this.producerTemplate = camelContext.createProducerTemplate();
    configurarCamelContext();
    if (CHAT_ID == null) {
      obtenerChatId();
    }
  }

  private void configurarCamelContext() {
    try {
      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() {
          from("telegram:bots?authorizationToken=" + AUTHORIZATION_TOKEN)
                  .to("log:INFO?showHeaders=true")
                  .process(exchange -> {
                    String receivedChatId = exchange.getIn().getHeader("CamelTelegramChatId", String.class);
                    if (receivedChatId != null) {
                      CHAT_ID = receivedChatId;
                      System.out.println("Chat ID obtenido: " + CHAT_ID);
                    }
                  });

          from("direct:sendToTelegram")
                  .choice()
                  .when(exchange -> CHAT_ID != null)
                  .to("telegram:bots?authorizationToken=" + AUTHORIZATION_TOKEN + "&chatId=" + CHAT_ID)
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
      System.out.println("Esperando recibir un mensaje para obtener el Chat ID...");
      Thread.sleep(10000); // Espera de 10 segundos para recibir mensajes
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enviarMensaje(String receptor, String asunto, String cuerpo) {
    try {
      producerTemplate.sendBody("direct:sendToTelegram", asunto + "\n" + cuerpo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

