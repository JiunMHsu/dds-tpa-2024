package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Mensaje;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.whatsapp.model.TextMessageRequest;
import org.apache.camel.component.whatsapp.model.TextMessage;

import java.time.LocalDateTime;

public class WhatsAppSender implements INotificador {
  private static String PHONE_NUMBER_ID; // Deberia ser el bot por ahora lo dejo asi
  private static String AUTHORIZATION_TOKEN;

  @Override
  public void enviarMensaje(String mensaje, Contacto contacto) {
    try {
      CamelContext camelContext = new DefaultCamelContext();

      camelContext.addRoutes(new RouteBuilder() {
        @Override
        public void configure() {
          from("direct:start")
              .process(exchange -> {
                TextMessageRequest request = new TextMessageRequest();
                request.setTo(contacto.getWhatsApp());
                request.setText(new TextMessage());
                request.getText().setBody(mensaje);

                exchange.getIn().setBody(request);
              })
              .toF("whatsapp:%s?authorizationToken=%s", PHONE_NUMBER_ID, AUTHORIZATION_TOKEN);
        }
      });

      camelContext.start();
      camelContext.createProducerTemplate().sendBody("direct:start", "");
      camelContext.stop();

      Mensaje registroMensaje = Mensaje.create(
          mensaje,
          LocalDateTime.now(),
          contacto,
          MedioDeNotificacion.WHATSAPP);

      MensajeRepository.agregar(registroMensaje);

    } catch (Exception error) {
      error.printStackTrace();
    }
  }
}