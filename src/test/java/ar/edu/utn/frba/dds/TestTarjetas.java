package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.heladera.ExcepcionCapacidadExcedida;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tarjeta.*;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;

import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTarjetas {

  private TarjetaPersonaVulnerable tarjeta1;
  private TarjetaPersonaVulnerable tarjeta2;
  private TarjetaPersonaVulnerable tarjeta3;

  private PersonaVulnerable persona1;
  private PersonaVulnerable persona2;
  private PersonaVulnerable persona3;

  private Heladera heladera1;

  @BeforeEach
  public void setup() {
    persona1 = PersonaVulnerable.with("", 1);
    persona2 = PersonaVulnerable.with("", 2);
    persona3 = PersonaVulnerable.with("", 0);

    tarjeta1 = TarjetaPersonaVulnerable.with(persona1);
    tarjeta2 = TarjetaPersonaVulnerable.with(persona2);
    tarjeta3 = TarjetaPersonaVulnerable.with(persona3);

    heladera1 = Heladera.with(8);
    heladera1.setEstado(EstadoHeladera.ACTIVA);
  }


  @Test
  @DisplayName("Se generan codigos no repetidos")
  public void comprobarCodigosRepetidos() {
    tarjeta1 = TarjetaPersonaVulnerable.with();
    tarjeta2 = TarjetaPersonaVulnerable.with();
    tarjeta3 = TarjetaPersonaVulnerable.with();

    List<TarjetaPersonaVulnerable> tarjetasExistentes = new ArrayList<>();
    tarjetasExistentes.add(tarjeta1);
    tarjetasExistentes.add(tarjeta2);
    tarjetasExistentes.add(tarjeta3);

    String nuevoCodigo = GeneradorDeCodigosTarjeta.generar();

    Assertions.assertFalse(tarjetasExistentes.stream().anyMatch(tarjeta -> tarjeta.getCodigo().equals(nuevoCodigo)),
        "El código generado ya existe en la lista de tarjetas existentes.");

  }

  @Test
  @DisplayName("Usos de la tarjeta")
  public void puedeUsar() {

    Assertions.assertEquals(6, tarjeta1.usosPorDia(),
        "La tarjeta1 pertenece a una persona con 1 menor a cargo, por lo que tiene 6 usos diarios.");

    Assertions.assertEquals(8, tarjeta2.usosPorDia(),
        "La tarjeta2 pertenece a una persona con 2 menor a cargo, por lo que tiene 8 usos diarios.");

    Assertions.assertEquals(4, tarjeta3.usosPorDia(),
        "La tarjeta3 pertenece a una persona sin menor a cargo, por lo que tiene 4 usos diarios.");

    try {
      tarjeta3.registrarUso(heladera1);
      tarjeta3.registrarUso(heladera1);
      tarjeta3.registrarUso(heladera1);
      tarjeta3.registrarUso(heladera1);
      tarjeta3.registrarUso(heladera1);

      Assertions.fail("No tiro Excepción.");
    } catch (ExcepcionUsoInvalido e) {
      Assertions.assertNotNull(e, e.getMessage());
    }
  }

  @Test
  @DisplayName("Uso tarjeta en una heladera")
  public void registrarUsoTarjeta() {
    try {
      heladera1.agregarViandas(4);
    } catch (ExcepcionCapacidadExcedida e) {
      Assertions.fail("Capacidad excedida.");
    }
    Assertions.assertEquals(4, heladera1.getViandas());

    try {
      tarjeta3.registrarUso(heladera1);
      Assertions.assertEquals(3, heladera1.getViandas(),
          "Al registrar el uso de una tarjeta, la cantidad de viandas en la Heladera usada decrementa.");

    } catch (ExcepcionUsoInvalido e) {
      Assertions.fail("No se pudo registrar el uso de la tarjeta.");
    }
  }
}

