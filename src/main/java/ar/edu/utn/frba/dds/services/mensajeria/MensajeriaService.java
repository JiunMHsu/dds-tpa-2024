package ar.edu.utn.frba.dds.services.mensajeria;

import ar.edu.utn.frba.dds.models.entities.mensajeria.EmailSender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.ISender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.mensajeria.TelegramSender;
import ar.edu.utn.frba.dds.models.entities.mensajeria.WhatsAppSender;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;


public class MensajeriaService {
  private final MensajeRepository mensajeRepository;
  private final Map<MedioDeNotificacion, ISender> senders;

  public MensajeriaService(MensajeRepository mensajeRepository) {
    this.mensajeRepository = mensajeRepository;
    Map<MedioDeNotificacion, ISender> senders = new EnumMap<>(MedioDeNotificacion.class);

    senders.put(MedioDeNotificacion.EMAIL, new EmailSender());
    senders.put(MedioDeNotificacion.WHATSAPP, new WhatsAppSender());
    senders.put(MedioDeNotificacion.TELEGRAM, new TelegramSender());

    this.senders = senders;
  }

  public void enviarMensaje(Mensaje mensaje) {
    MedioDeNotificacion medio = mensaje.getContacto().getMedioDeNotificacion();
    ISender sender = senders.get(medio);
    if (sender == null) {
      throw new UnsupportedOperationException("Medio de notificación no soportado: " + medio);
    }

    try {
      sender.enviarMensaje(mensaje);
      mensaje.setFechaEnvio(LocalDateTime.now());
      mensajeRepository.guardar(mensaje);
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el mensaje: " + e.getMessage(), e);
    }
  }

}
