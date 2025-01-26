package ar.edu.utn.frba.dds.services.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensajeria.ISender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.ISenderFactory;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import java.time.LocalDateTime;

/**
 * Servicio de mensajería.
 */
public class MensajeriaService {
  private final MensajeRepository mensajeRepository;
  private final ISenderFactory senderFactory;

  /**
   * Constructor.
   *
   * @param mensajeRepository repositorio de mensajes
   * @param senderFactory     fábrica de senders
   */
  public MensajeriaService(MensajeRepository mensajeRepository, ISenderFactory senderFactory) {
    this.mensajeRepository = mensajeRepository;
    this.senderFactory = senderFactory;
  }

  /**
   * Envía un mensaje a un contacto.
   *
   * @param mensaje mensaje a enviar
   */
  public void enviarMensaje(Mensaje mensaje) {
    MedioDeNotificacion medio = mensaje.getContacto().getMedioDeNotificacion();
    ISender sender = senderFactory.create(medio);

    try {
      sender.enviarMensaje(mensaje);
      mensaje.setFechaEnvio(LocalDateTime.now());
      mensajeRepository.guardar(mensaje);
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el mensaje: " + e.getMessage(), e);
    }
  }

}
