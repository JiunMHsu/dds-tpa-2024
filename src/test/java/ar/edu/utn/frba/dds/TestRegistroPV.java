package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.tarjeta.GeneradorDeCodigo;
import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestRegistroPV {

  @Test
  @DisplayName("Test del registro de persona vulnerable")

  public void testResgistroPersonaVulnerable() {

    Ubicacion ubicacionTest = new Ubicacion(48, 84);
    Direccion direccionTest = new Direccion("Aguero", 1281, ubicacionTest);
    Documento documentoTest = new Documento(12345678, TipoDocumento.DNI);

    // Personas Vulnerables
    PersonaVulnerable personaRegistrada = PersonaVulnerable.with("Joaquin", direccionTest, documentoTest, 2);
    personaRegistrada.setFechaNacimiento(LocalDate.of(2000, 1, 1));
    personaRegistrada.setFechaRegistro(LocalDate.of(2023, 5, 1));

    PersonaVulnerable personaNoRegistrada = PersonaVulnerable.with("Matias", direccionTest, documentoTest, 2);
    personaNoRegistrada.setFechaNacimiento(LocalDate.of(2010, 2, 23));
    personaNoRegistrada.setFechaRegistro(LocalDate.of(2022, 4, 12));


    // Colaborador

    List<TipoColaboracion> colaboraciones = Collections.singletonList(TipoColaboracion.ENTREGA_TARJETAS);

    Persona personaColaborador = Persona.persona();
    personaColaborador.setFormaDeColaborar(colaboraciones);

    Persona personaQueNoRegistro = Persona.persona();
    personaQueNoRegistro.setFormaDeColaborar(colaboraciones);

    // Resgistro
    Tarjeta tarjeta = Tarjeta.with(GeneradorDeCodigo.generadorCodigo());
    RepartoDeTarjetas repartoTest = new RepartoDeTarjetas(personaColaborador, tarjeta, personaRegistrada);

    Assertions.assertEquals(personaColaborador, repartoTest.getColaborador(),
        "La persona que le entregó la tarjeta a personaRegistrada debería ser personaColaborador.");

    Assertions.assertNotEquals(personaQueNoRegistro, repartoTest.getColaborador(),
        "La persona que le entregó la tarjeta a personaRegistrada no debería ser personaQueNoRegistro.");

    Assertions.assertEquals(personaColaborador, repartoTest.getColaborador(),
        "La persona le entrego una tarjeta y fue registrada correctament.");
  }
}
