package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.AppConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.whatsapp.model.TextMessage;
import org.apache.camel.component.whatsapp.model.TextMessageRequest;
import org.apache.camel.impl.DefaultCamelContext;

@Getter
@Setter
public class WhatsAppSender implements Sender {

  private final String phoneNumberId;
  private final String authorizationToken;
  private CamelContext camelContext;

  public WhatsAppSender(String phoneNumberId, String authorizationToken) {
    this.phoneNumberId = phoneNumberId;
    this.authorizationToken = authorizationToken;
    this.camelContext = new DefaultCamelContext();
    setupRoutes();
  }

  public WhatsAppSender() {
    this.phoneNumberId = AppConfig.getProperty("WHATSAPP_PHONE_NUMBER_ID");
    this.authorizationToken = AppConfig.getProperty("WHATSAPP_AUTHORIZATION_TOKEN");
    this.camelContext = new DefaultCamelContext();
    setupRoutes();
  }

  private void setupRoutes() {
    try {
      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() {
          from("direct:start")
              .toF("whatsapp:%s?authorizationToken=%s", phoneNumberId, authorizationToken);
        }
      });
      camelContext.start();
      System.out.println("CamelContext configurado e iniciado correctamente.");
    } catch (Exception e) {
      System.err.println("Error al configurar CamelContext: " + e.getMessage());
    }
  }

  @Override
  public void enviarMensaje(String receptor, String asunto, String cuerpo) {
    try {
      TextMessageRequest request = new TextMessageRequest();
      request.setTo(receptor);
      request.setText(new TextMessage());
      request.getText().setPreviewUrl(false);
      request.getText().setBody(asunto + "\n" + cuerpo);

      ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
      producerTemplate.requestBody("direct:start", request);

    } catch (Exception e) {
      System.err.println("Error al enviar el mensaje: " + e.getMessage());
    } finally {
      stopContext();
    }
  }

  public void stopContext() {
    try {
      if (camelContext != null) {
        camelContext.stop();
      }
    } catch (Exception e) {
      System.err.println("Error al detener CamelContext: " + e.getMessage());
    }
  }
}