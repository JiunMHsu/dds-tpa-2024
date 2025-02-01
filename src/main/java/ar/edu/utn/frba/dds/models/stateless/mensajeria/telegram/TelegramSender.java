package ar.edu.utn.frba.dds.models.stateless.mensajeria.telegram;

import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.ISender;
import ar.edu.utn.frba.dds.utils.AppProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Telegram sender class.
 */
@Getter
@Setter
public class TelegramSender implements ISender {
  private final String authorizationToken;
  private final String chatId;
  private CamelContext camelContext;
  private ProducerTemplate producerTemplate;

  /**
   * Constructor de TelegramSender.
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
      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() throws Exception {
          from("telegram:bots?authorizationToken=" + authorizationToken)
              .process(exchange -> {
                String receivedChatId = ((Map<String, Object>) body.get("chat")).get("id").toString();

                if (receivedChatId != null) {
                  String backendUrl = "http://localhost:8081/api/vincular-telegram?chatId=" + receivedChatId;
                  exchange.getIn().setHeader(Exchange.HTTP_QUERY, "chatId=" + receivedChatId);
                  exchange.getIn().setHeader(Exchange.HTTP_METHOD, "POST");
                  producerTemplate.sendBody("direct:sendToBackend", backendUrl);
                } else {
                  exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
                }
              })
              .to("http://localhost:8081/api/vincular-telegram?bridgeEndpoint=true");  // Línea restaurada
        }
      });
      camelContext.start();
      this.producerTemplate = camelContext.createProducerTemplate();

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
      String mensajeConcatenado = mensaje.getAsunto() + "\n\n" + mensaje.getCuerpo();

      Map<String, Object> headers = new HashMap<>();
      headers.put("chatId", receptor);

      producerTemplate.sendBodyAndHeaders("direct:sendToTelegram", mensajeConcatenado, headers);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}