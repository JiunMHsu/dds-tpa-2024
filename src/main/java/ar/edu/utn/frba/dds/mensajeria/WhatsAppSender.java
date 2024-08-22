package ar.edu.utn.frba.dds.mensajeria;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.whatsapp.model.TextMessage;
import org.apache.camel.component.whatsapp.model.TextMessageRequest;
import org.apache.camel.impl.DefaultCamelContext;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class WhatsAppSender {

    private final String phoneNumberId;
    private final String authorizationToken;
    private CamelContext camelContext;

    public WhatsAppSender(String phoneNumberId, String authorizationToken) {
        this.phoneNumberId = phoneNumberId;
        this.authorizationToken = authorizationToken;
    }

    public void enviarMensaje(String receptor, String asunto, String cuerpo) {
        try {
            if (camelContext == null) {
                camelContext = new DefaultCamelContext();
                configurarRuta();
                camelContext.start();
                System.out.println("CamelContext configurado e iniciado correctamente.");
            }

            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            Exchange exchange = producerTemplate.send("direct:start", e -> {
                TextMessageRequest request = new TextMessageRequest();
                request.setTo(receptor);
                request.setText(new TextMessage());
                request.getText().setBody(cuerpo);

                e.getIn().setBody(request);
            });

            String response = exchange.getMessage().getBody(String.class);
            System.out.println("Respuesta de la API: " + response);

        } catch (Exception e) {

            System.err.println("Error al enviar el mensaje: " + e.getMessage());

        } finally {
            try {
                if (camelContext != null) {
                    camelContext.stop();
                }
            } catch (Exception e) {

                System.err.println("Error al detener CamelContext: " + e.getMessage());
            }
        }
    }

    private void configurarRuta() throws Exception {
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .log("Enviando mensaje a WhatsApp con encabezados: ${headers}")
                        .toF("whatsapp:%s/?authorizationToken=%s", phoneNumberId, authorizationToken)
                        .log("Respuesta de WhatsApp: ${body}");
            }
        });
    }
}
