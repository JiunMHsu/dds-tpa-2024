package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Mensaje;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSuscripciones {

  Colaborador unColaborador;
  Heladera unaHeladera;
  SuscripcionFallaHeladera suscripcionPorFalla;
  SuscripcionFaltaVianda suscripcionPorFaltaVianda;

  @BeforeEach
  public void setup() {
    unColaborador = Colaborador.colaborador(Usuario.empty());
    unColaborador.setContacto(Contacto.with(
        "unemail@example.com",
        "12345678",
        "1169228334",
        "1169228334")
    );

    unaHeladera = Heladera.with(20);
    unaHeladera.setNombre("UTN Medrano");

    suscripcionPorFalla = new SuscripcionFallaHeladera(
        unColaborador,
        unaHeladera,
        MedioDeNotificacion.WHATSAPP
    );

    suscripcionPorFaltaVianda = new SuscripcionFaltaVianda(
        unColaborador,
        unaHeladera,
        MedioDeNotificacion.WHATSAPP,
        9
    );
  }

  @Test
  @DisplayName("Un Colaborador puede suscribirse a una heladera")
  public void suscripcion() {
    Assertions.assertFalse(unaHeladera.getObserversMovimientoVianda().contains(suscripcionPorFaltaVianda));

    suscripcionPorFaltaVianda.suscribirAHeladera();
    Assertions.assertTrue(unaHeladera.getObserversMovimientoVianda().contains(suscripcionPorFaltaVianda));
  }

//  @Test
//  @DisplayName("Cuando un determinado evento ocurre, los suscriptores de dicho evento son notificados")
//  public void notificadoDelEvento() {
//    suscripcionPorFalla.suscribirAHeladera();
//    Incidente unIncidente = Incidente.of(TipoIncidente.FRAUDE, unaHeladera, LocalDateTime.now());
//    unIncidente.reportar();
//
//    List<Mensaje> mensajesRegistrados = MensajeRepository.obtenerTodos();
//    Mensaje mensajeEnviado = mensajesRegistrados.stream()
//        .filter(m -> m.getMedio().equals(MedioDeNotificacion.WHATSAPP) && m.getDestinatario().equals(unColaborador))
//        .findFirst().orElse(null);
//
//    Assertions.assertNotNull(mensajeEnviado);
//    Assertions.assertEquals("La Heradera UTN Medrano sufrió fallas", mensajeEnviado.getBody());
//  }
}
