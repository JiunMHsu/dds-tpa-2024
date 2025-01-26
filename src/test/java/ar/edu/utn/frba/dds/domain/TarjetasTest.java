package ar.edu.utn.frba.dds.domain;

import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeCodigosTarjeta;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TarjetasTest {

  private TarjetaPersonaVulnerable tarjeta1;
  private TarjetaPersonaVulnerable tarjeta2;
  private TarjetaPersonaVulnerable tarjeta3;
  private Heladera heladera1;

  @BeforeEach
  public void setup() {
    PersonaVulnerable persona1 = PersonaVulnerable.con("", 1);
    PersonaVulnerable persona2 = PersonaVulnerable.con("", 2);
    PersonaVulnerable persona3 = PersonaVulnerable.con("", 0);

    tarjeta1 = TarjetaPersonaVulnerable.de(persona1);
    tarjeta2 = TarjetaPersonaVulnerable.de(persona2);
    tarjeta3 = TarjetaPersonaVulnerable.de(persona3);

    heladera1 = Heladera.con(8);
    heladera1.setEstado(EstadoHeladera.ACTIVA);
  }

  @Test
  @DisplayName("Se generan codigos no repetidos")
  public void comprobarCodigosRepetidos() {
    tarjeta1 = TarjetaPersonaVulnerable.de();
    tarjeta2 = TarjetaPersonaVulnerable.de();
    tarjeta3 = TarjetaPersonaVulnerable.de();

    List<TarjetaPersonaVulnerable> tarjetasExistentes = new ArrayList<>();
    tarjetasExistentes.add(tarjeta1);
    tarjetasExistentes.add(tarjeta2);
    tarjetasExistentes.add(tarjeta3);

    String nuevoCodigo = ValidadorDeCodigosTarjeta.generar();

    Assertions.assertFalse(tarjetasExistentes.stream().anyMatch(tarjeta -> tarjeta.getCodigo().equals(nuevoCodigo)),
        "El código generado ya existe en la lista nueva tarjetas existentes.");

  }

  @Test
  @DisplayName("Autorizado nueva uso nueva Personas Vulnerables")
  public void personaVulnerablePuedeUsar() {

    Assertions.assertEquals(6, tarjeta1.usosPorDia(),
        "La tarjeta1 pertenece a una persona paraColaborador 1 menor a cargo, nueva lo que tiene 6 usos diarios.");

    Assertions.assertEquals(8, tarjeta2.usosPorDia(),
        "La tarjeta2 pertenece a una persona paraColaborador 2 menor a cargo, nueva lo que tiene 8 usos diarios.");

    Assertions.assertEquals(4, tarjeta3.usosPorDia(),
        "La tarjeta3 pertenece a una persona sin menor a cargo, nueva lo que tiene 4 usos diarios.");

    tarjeta3.sumarUso();
    tarjeta3.sumarUso();
    tarjeta3.sumarUso();
    tarjeta3.sumarUso();

    Assertions.assertFalse(tarjeta3.puedeUsarseEn(heladera1));
  }

  @Test
  @DisplayName("Autorizado nueva uso nueva Colaboradores")
  public void colaboradoresPuedeUsar() {
    // TODO
    // Entiendo que esto es nueva medio nueva broker
    // Igualmente agrego una funcion
  }
}

