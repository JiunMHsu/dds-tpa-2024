package ar.edu.utn.frba.dds.mensajeria;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.whatsapp.model.TextMessage;
import org.apache.camel.component.whatsapp.model.TextMessageRequest;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsAppSender implements Sender {

    private final String PHONE_NUMBER_ID;
    private final String AUTHORIZATION_TOKEN;
    private CamelContext camelContext;

    public WhatsAppSender(String phoneNumberID, String authorizationToken) {
        this.PHONE_NUMBER_ID = phoneNumberID;
        this.AUTHORIZATION_TOKEN = authorizationToken;
        this.camelContext = new DefaultCamelContext();
        configurarCamelContext();
    }

    private void configurarCamelContext() {
        try {
            this.camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("direct:start")
                            .toF("whatsapp:%s?authorizationToken=%s", getPHONE_NUMBER_ID(), getAUTHORIZATION_TOKEN());
                }
            });
            this.camelContext.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enviarMensaje(String receptor, String asunto, String cuerpo) {
        try {
            camelContext.createProducerTemplate().send("direct:start", exchange -> {
                TextMessageRequest request = new TextMessageRequest();
                request.setTo(receptor);
                request.setText(new TextMessage());
                request.getText().setBody(cuerpo);
                exchange.getIn().setBody(request);
            });
        } catch (Exception e) {
            System.err.println("Error al enviar el mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
