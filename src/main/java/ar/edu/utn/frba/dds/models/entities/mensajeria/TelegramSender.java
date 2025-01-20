package ar.edu.utn.frba.dds.models.entities.mensajeria;

import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.utils.AppProperties;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TelegramSender implements ISender {
  private final String AUTHORIZATION_TOKEN;
  private final String CHAT_ID;
  private CamelContext camelContext;
  private ProducerTemplate producerTemplate;

  public TelegramSender() {
    this.AUTHORIZATION_TOKEN = AppProperties.getInstance().propertyFromName("TELEGRAM_AUTHORIZATION_TOKEN");
    this.CHAT_ID = AppProperties.getInstance().propertyFromName("TELEGRAM_CHAT_ID");
    configurarCamelContext();
  }

  private void configurarCamelContext() {
    if (camelContext != null) {
      return;
    }

    try {
      this.camelContext = new DefaultCamelContext();
      this.producerTemplate = camelContext.createProducerTemplate();

      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() {
          from("direct:sendToTelegram")
              .choice()
              .when(exchange -> CHAT_ID != null)
              .toF("telegram:bots/?authorizationToken=%s&chatId=%s", AUTHORIZATION_TOKEN, CHAT_ID)
              .otherwise()
              .log("Chat ID no disponible. No se puede enviar el mensaje.");
        }
      });

      camelContext.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enviarMensaje(Contacto contacto, String asunto, String cuerpo) {

    String receptor = contacto.getContacto();
    if (receptor == null)
      throw new IllegalArgumentException("El contacto no tiene una cuenta de Telegram asociada");

    if (camelContext == null || producerTemplate == null) {
      configurarCamelContext();
    }

    try {
      if (CHAT_ID != null) {
        String mensaje = asunto + "\n\n" + cuerpo;
        producerTemplate.sendBody("direct:sendToTelegram", mensaje);
      } else {
        System.out.println("Chat ID aún no disponible. No se puede enviar el mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}