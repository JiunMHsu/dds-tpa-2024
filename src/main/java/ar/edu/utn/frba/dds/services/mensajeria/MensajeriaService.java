package ar.edu.utn.frba.dds.services.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.ISender;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.ISenderFactory;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;

/**
 * Servicio de mensajería.
 */
public class MensajeriaService implements WithSimplePersistenceUnit {
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

      beginTransaction();
      mensajeRepository.guardar(mensaje);
      commitTransaction();
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el mensaje: " + e.getMessage(), e);
    }
  }

}
