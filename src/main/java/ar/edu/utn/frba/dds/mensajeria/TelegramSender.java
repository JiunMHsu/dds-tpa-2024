package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Mensaje;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

import java.time.LocalDateTime;

public class TelegramSender implements INotificador {

  private static String TELEGRAM_ENDPOINT; // Deberia ser el bot por ahora lo dejo asi

  @Override
  public void enviarMensaje(String mensaje, Contacto contacto) {

    CamelContext context = new DefaultCamelContext();

    try {
      context.start();
      ProducerTemplate producerTemplate = context.createProducerTemplate();
      producerTemplate.sendBody(TELEGRAM_ENDPOINT, mensaje);

      // Repository
      Mensaje mensajeObj = Mensaje.builder()
              .body(mensaje)
              .fechaEnvio(LocalDateTime.now())
              .destinatario(contacto)
              .medio(MedioDeNotificacion.TELEGRAM)
              .build();

      MensajeRepository.agregar(mensajeObj);

    } catch (Exception error) {
      error.printStackTrace();

    } finally {
      try {
        context.stop();
      } catch (Exception error) {
        error.printStackTrace();
      }
    }
  }
}