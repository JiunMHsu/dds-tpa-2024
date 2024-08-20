package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;

import java.time.LocalDateTime;

public class WhatsAppSender implements Sender {
  private static String PHONE_NUMBER_ID; // Deberia ser el bot por ahora lo dejo asi
  private static String AUTHORIZATION_TOKEN;

  @Override
  public void enviarMensaje(String receptor, String asunto, String cuerpo) {
    try {
//      CamelContext camelContext = new DefaultCamelContext();
//
//      camelContext.addRoutes(new RouteBuilder() {
//        @Override
//        public void configure() {
//          from("direct:start")
//              .process(exchange -> {
//                TextMessageRequest request = new TextMessageRequest();
//                request.setTo(contacto.getWhatsApp());
//                request.setText(new TextMessage());
//                request.getText().setBody(mensaje);
//
//                exchange.getIn().setBody(request);
//              })
//              .toF("whatsapp:%s?authorizationToken=%s", PHONE_NUMBER_ID, AUTHORIZATION_TOKEN);
//        }
//      });
//
//      camelContext.start();
//      camelContext.createProducerTemplate().sendBody("direct:start", "");
//      camelContext.stop();

      Mensaje registroMensaje = Mensaje.create(
          receptor,
          LocalDateTime.now(),
          contacto,
          MedioDeNotificacion.WHATSAPP);

      MensajeRepository.agregar(registroMensaje);

    } catch (Exception error) {
      error.printStackTrace();
    }
  }
}