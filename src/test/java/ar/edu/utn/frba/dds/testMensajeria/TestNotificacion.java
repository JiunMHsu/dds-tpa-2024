package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.*;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Mensaje;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNotificacion {
  private String mensaje;
  private Contacto contacto;
  private LocalDateTime fecha;

  @BeforeEach
  void setUp() {
    mensaje = "Test Notificador";
    fecha = LocalDateTime.now();

    MensajeRepository.limpiar();
  }

  @Test
  void testEnviarMensajeWhatsApp() {
    contacto = Contacto.ofWhatsApp("123456789");
    INotificador notificador = NotificadorFactory.of(MedioDeNotificacion.WHATSAPP);
    notificador.enviarMensaje(mensaje, contacto);

    List<Mensaje> mensajes = MensajeRepository.obtenerTodos();
    assertEquals(1, mensajes.size());
    Mensaje mensajeGuardado = mensajes.get(0);
    assertEquals(mensaje, mensajeGuardado.getBody());
    assertEquals(MedioDeNotificacion.WHATSAPP, mensajeGuardado.getMedio());
    assertEquals(contacto, mensajeGuardado.getDestinatario());
    assertEquals(fecha.withNano(0), mensajeGuardado.getFechaEnvio().withNano(0));
  }

  @Test
  void testEnviarMensajeTelegram() {
    contacto = Contacto.ofTelegram("telegramUser");
    INotificador notificador = NotificadorFactory.of(MedioDeNotificacion.TELEGRAM);
    notificador.enviarMensaje(mensaje, contacto);

    List<Mensaje> mensajes = MensajeRepository.obtenerTodos();
    assertEquals(1, mensajes.size());
    Mensaje mensajeGuardado = mensajes.get(0);
    assertEquals(mensaje, mensajeGuardado.getBody());
    assertEquals(MedioDeNotificacion.TELEGRAM, mensajeGuardado.getMedio());
    assertEquals(contacto, mensajeGuardado.getDestinatario());
    assertEquals(fecha.withNano(0), mensajeGuardado.getFechaEnvio().withNano(0));
  }

  @Test
  void testEnviarMensajeMail() {
    contacto = Contacto.with("utn.dds.g22@gmail.com", "113242069", "", "");
    INotificador notificador = NotificadorFactory.of(MedioDeNotificacion.MAIL);
    notificador.enviarMensaje(mensaje, contacto);

    List<Mensaje> mensajes = MensajeRepository.obtenerTodos();
    assertEquals(1, mensajes.size());
    Mensaje mensajeGuardado = mensajes.get(0);
    assertEquals(mensaje, mensajeGuardado.getBody());
    assertEquals(MedioDeNotificacion.MAIL, mensajeGuardado.getMedio());
    assertEquals(contacto, mensajeGuardado.getDestinatario());
    assertEquals(fecha.withNano(0), mensajeGuardado.getFechaEnvio().withNano(0));

  }
}

