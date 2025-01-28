package ar.edu.utn.frba.dds.models.stateless.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.utils.AppProperties;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Telegram sender class.
 */
public class TelegramSender implements ISender {
  private final String authorizationToken;
  private final String chatId;
  private CamelContext camelContext;
  private ProducerTemplate producerTemplate;

  /**
   * Constructor.
   */
  public TelegramSender() {
    this.authorizationToken = AppProperties.getInstance().propertyFromName("TELEGRAM_AUTHORIZATION_TOKEN");
    this.chatId = AppProperties.getInstance().propertyFromName("TELEGRAM_CHAT_ID");
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
              .when(exchange -> chatId != null)
              .toF("telegram:bots/?authorizationToken=%s&chatId=%s", authorizationToken, chatId)
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
  public void enviarMensaje(Mensaje mensaje) {

    String receptor = mensaje.getContacto().getValor();
    if (receptor == null) {
      throw new IllegalArgumentException("El contacto no tiene una cuenta de Telegram asociada");
    }

    if (camelContext == null || producerTemplate == null) {
      configurarCamelContext();
    }

    try {
      if (chatId != null) {
        String mensajeConcatenado = mensaje.getAsunto() + "\n\n" + mensaje.getCuerpo();
        producerTemplate.sendBody("direct:sendToTelegram", mensajeConcatenado);
      } else {
        System.out.println("Chat ID aún no disponible. No se puede enviar el mensaje.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}