package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Mensaje;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.repository.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.repository.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestIncidentes {

  Tecnico tecnico1;
  Tecnico tecnico2;
  Tecnico tecnico3;
  Heladera heladera;
  Incidente incidente;

  @BeforeEach
  public void setup() {
    tecnico1 = Tecnico.with(
        "t1",
        Contacto.ofWhatsApp(""),
        MedioDeNotificacion.WHATSAPP,
        new Area(new Ubicacion(-34.615803, -58.433298), 9.0));
    TecnicoRepository.agregar(tecnico1);

    tecnico2 = Tecnico.with(
        "t2",
        Contacto.ofTelegram(""),
        MedioDeNotificacion.TELEGRAM,
        new Area(new Ubicacion(-34.615803, -58.433298), 6.0));
    TecnicoRepository.agregar(tecnico2);

    tecnico3 = Tecnico.with(
        "t3",
        Contacto.ofWhatsApp(""),
        MedioDeNotificacion.WHATSAPP,
        new Area(new Ubicacion(-31.420083, -64.188776), 5.0));
    TecnicoRepository.agregar(tecnico3);

    heladera = Heladera.with("Medrano UTN",
        Direccion.with(new Calle(""), 0, new Ubicacion(-34.603722, -58.381592)),
        70);
  }

  @Test
  @DisplayName("Un incidente puede ser reportado")
  public void reporteIncidente() {
    incidente = Incidente.of(TipoIncidente.FRAUDE, heladera, LocalDateTime.now());
    incidente.reportar();

    Assertions.assertEquals(EstadoHeladera.INACTIVA, heladera.getEstado());
    Assertions.assertTrue(IncidenteRepository.obtenerTodos().contains(incidente));
  }

  @Test
  @DisplayName("Cuando un incidente es reportado, el Técnico más cercano es notificado.")
  public void avisoTecnico() {
    LocalDateTime fechaHora = LocalDateTime.now();
    incidente = Incidente.of(TipoIncidente.FRAUDE, heladera, fechaHora);
    incidente.reportar();

    List<Mensaje> mensajesRegistrados = MensajeRepository.obtenerTodos();
    Mensaje mensajeEnviado = mensajesRegistrados.stream()
        .filter(m -> m.getMedio().equals(MedioDeNotificacion.WHATSAPP) && m.getDestinatario().equals(tecnico1.getContacto()))
        .findFirst().orElse(null);

    Assertions.assertEquals("Incidente reportado: FRAUDE en la heladera: Medrano UTN a las: " + fechaHora, mensajeEnviado.getBody());
  }
}
