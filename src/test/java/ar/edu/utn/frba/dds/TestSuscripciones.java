package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.suscripcion.SuscripcionFaltaVianda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/*
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

    unaHeladera = Heladera.con(20);
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
    return;
    // Assertions.assertFalse(unaHeladera.getObserversMovimientoVianda().contains(suscripcionPorFaltaVianda));

    // suscripcionPorFaltaVianda.suscribirAHeladera();
    // Assertions.assertTrue(unaHeladera.getObserversMovimientoVianda().contains(suscripcionPorFaltaVianda));
  }

//  @Test
//  @DisplayName("Cuando un determinado evento ocurre, los suscriptores de dicho evento son notificados")
//  public void notificadoDelEvento() {
//    suscripcionPorFalla.suscribirAHeladera();
//    Incidente unIncidente = Incidente.de(TipoIncidente.FRAUDE, unaHeladera, LocalDateTime.now());
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
*/