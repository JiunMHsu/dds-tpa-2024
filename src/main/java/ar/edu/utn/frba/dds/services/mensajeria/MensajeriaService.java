package ar.edu.utn.frba.dds.services.mensajeria;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.*;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MensajeriaService {
  private final MensajeRepository mensajeRepository;
  private final HeladeraService heladeraService;
  private final Map<MedioDeNotificacion, ISender> senders;

  public MensajeriaService(MensajeRepository mensajeRepository, HeladeraService heladeraService) {
    this.mensajeRepository = mensajeRepository;
    this.heladeraService = heladeraService;
    Map<MedioDeNotificacion, ISender> senders = new EnumMap<>(MedioDeNotificacion.class);

    senders.put(MedioDeNotificacion.EMAIL, new EmailSender());
    senders.put(MedioDeNotificacion.WHATSAPP, new WhatsAppSender());
    senders.put(MedioDeNotificacion.TELEGRAM, new TelegramSender());

    this.senders = senders;
  }

  public void notificacionFaltaVianda(SuscripcionFaltaVianda suscripcion) {
    String asunto = "Heladera con baja disponibilidad de viandas";
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s tiene solo %d viandas restantes. Por favor, lleve más viandas para reabastecerla.\n\n" +
            "Gracias por su colaboración.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        suscripcion.getViandasRestantes()
    );

    Mensaje mensaje = Mensaje.paraColaborador(
        suscripcion.getColaborador(),
        asunto,
        cuerpo
    );

    enviarPorMedio(mensaje, suscripcion.getMedioDeNotificacion());
  }

  public void notificacionHeladeraLlena(SuscripcionHeladeraLlena suscripcion) {
    String asunto = "Heladera casi llena";
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s está a punto de llenarse, con solo espacio para %d viandas más. " +
            "Por favor, redistribuir algunas viandas a otras heladeras.\n\n" +
            "Gracias por su colaboración.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        suscripcion.getEspacioRestante()
    );

    Mensaje mensaje = Mensaje.paraColaborador(
        suscripcion.getColaborador(),
        asunto,
        cuerpo
    );

    enviarPorMedio(mensaje, suscripcion.getMedioDeNotificacion());
  }

  public void notificacionFallaHeladera(SuscripcionFallaHeladera suscripcion) {
    String asunto = "Falla en la heladera";
    String sugerencias = this.heladerasActivasMasCercanas(suscripcion.getHeladera())
        .stream()
        .map(heladera -> heladera.getNombre())
        .collect(Collectors.joining("\n"));
    String cuerpo = String.format(
        "Estimado/a %s,\n\n" +
            "La %s ha sufrido un desperfecto.\n\n" +
            "Por favor, traslade las viandas a las siguientes heladeras sugeridas:\n\n" +
            "%s\n" +
            "Gracias por su rápida acción.",
        suscripcion.getColaborador().getNombre(),
        suscripcion.getHeladera().getNombre(),
        sugerencias
    );

    Mensaje mensaje = Mensaje.paraColaborador(
        suscripcion.getColaborador(),
        asunto,
        cuerpo
    );

    enviarPorMedio(mensaje, suscripcion.getMedioDeNotificacion());
  }

  public void enviarPorMedio(Mensaje mensaje, MedioDeNotificacion medio) {
    ISender sender = senders.get(medio);
    if (sender == null) {
      throw new UnsupportedOperationException("Medio de notificación no soportado: " + medio);
    }

    try {
      sender.enviarMensaje(mensaje.getContacto(), mensaje.getAsunto(), mensaje.getCuerpo());
      mensaje.setFechaEnvio(LocalDateTime.now());
      mensajeRepository.guardar(mensaje);
    } catch (Exception e) {
      throw new RuntimeException("Error al enviar el mensaje: " + e.getMessage(), e);
    }
  }

  public List<Heladera> heladerasActivasMasCercanas(Heladera heladera) {
    return heladeraService.buscarPorBarrio(heladera.getDireccion().getBarrio())
        .stream()
        .filter(Heladera::estaActiva)
        .toList();
  }
}
