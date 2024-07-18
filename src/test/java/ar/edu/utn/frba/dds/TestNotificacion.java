package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.*;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Mensaje;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNotificacion {

  @Mock
  private MensajeRepository mensajeRepository;

  private Contacto contacto;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    contacto = Contacto.empty();
  }

  private void verificarMensajeRegistrado(String mensaje, MedioDeNotificacion medio) {
    ArgumentCaptor<Mensaje> mensajeCaptor = ArgumentCaptor.forClass(Mensaje.class);
    verify(mensajeRepository, times(1)).agregar(mensajeCaptor.capture());

    Mensaje mensajeRegistrado = mensajeCaptor.getValue();
    assertEquals(mensaje, mensajeRegistrado.getBody());
    assertEquals(medio, mensajeRegistrado.getMedio());
    assertEquals(contacto, mensajeRegistrado.getDestinatario());
    assertEquals(LocalDateTime.now().getDayOfMonth(), mensajeRegistrado.getFechaEnvio().getDayOfMonth());
  }

  @Test
  public void testEnviarMensajeWhatsApp() {
    String mensaje = "Prueba por WhatsApp.";
    INotificador notificador = mock(WhatsAppSender.class);

    doNothing().when(notificador).enviarMensaje(mensaje, contacto);
    notificador.enviarMensaje(mensaje, contacto);

    verify(notificador, times(1)).enviarMensaje(mensaje, contacto);
    verificarMensajeRegistrado(mensaje, MedioDeNotificacion.WHATSAPP);
  }

  @Test
  public void testEnviarMensajeTelegram() {
    String mensaje = "Prueba por Telegram.";
    INotificador notificador = mock(TelegramSender.class);

    doNothing().when(notificador).enviarMensaje(mensaje, contacto);
    notificador.enviarMensaje(mensaje, contacto);

    verify(notificador, times(1)).enviarMensaje(mensaje, contacto);
    verificarMensajeRegistrado(mensaje, MedioDeNotificacion.TELEGRAM);
  }

  @Test
  public void testEnviarMensajeMail() {
    String mensaje = "Prueba por Mail.";
    INotificador notificador = mock(MailSender.class);

    doNothing().when(notificador).enviarMensaje(mensaje, contacto);
    notificador.enviarMensaje(mensaje, contacto);

    verify(notificador, times(1)).enviarMensaje(mensaje, contacto);
    verificarMensajeRegistrado(mensaje, MedioDeNotificacion.MAIL);
  }
}
