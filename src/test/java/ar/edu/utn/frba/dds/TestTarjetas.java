package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tarjeta.*;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;

import ar.edu.utn.frba.dds.utils.GeneradorDeCodigoTarjeta;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTarjetas {

  private TarjetaPersonaVulnerable tarjeta1;
  private TarjetaPersonaVulnerable tarjeta2;
  private TarjetaPersonaVulnerable tarjeta3;
  private List<TarjetaPersonaVulnerable> tarjetasExistentes;


  private PersonaVulnerable persona1;
  private PersonaVulnerable persona2;
  private PersonaVulnerable persona3;

  private Heladera heladera1;

  private LocalTime mediaNoche = LocalTime.of(0, 0);

  @Test
  @DisplayName("Se generan codigos no repetidos")
  public void comprobarCodigosRepetidos() {
    tarjeta1 = TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar());
    tarjeta2 = TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar());
    tarjeta3 = TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar());

    tarjetasExistentes = new ArrayList<>();
    tarjetasExistentes.add(tarjeta1);
    tarjetasExistentes.add(tarjeta2);
    tarjetasExistentes.add(tarjeta3);

    String nuevoCodigo = GeneradorDeCodigoTarjeta.generar();

    Assertions.assertFalse(tarjetasExistentes.stream().anyMatch(tarjeta -> tarjeta.getCodigo().equals(nuevoCodigo)),
        "El código generado ya existe en la lista de tarjetas existentes.");

  }

  @Test
  @DisplayName("Usos de la tarjeta")
  public void puedeUsar() {

    persona1 = PersonaVulnerable.with(1);
    persona2 = PersonaVulnerable.with(0);
    persona3 = PersonaVulnerable.with(0);

    tarjeta1 = TarjetaPersonaVulnerable.with(persona1, 5);
    tarjeta1.calcularUsosTarjeta(tarjeta1);

    tarjeta2 = TarjetaPersonaVulnerable.with(persona2, 0);
    tarjeta2.calcularUsosTarjeta(tarjeta2);

    tarjeta3 = TarjetaPersonaVulnerable.with(persona3, 0, mediaNoche);
    tarjeta3.calcularUsosTarjeta(tarjeta3);
    tarjeta3.verificarMedianoche();

    // Tiene 6 usos por dia (persona con 1 menor a cargo) y le quedan 5 por usar
    Assertions.assertTrue(tarjeta1.getUsosPorDia() == 6);
    Assertions.assertTrue(tarjeta1.puedeUsar());

    // Tiene 4 usos por dia (persona sin menores a cargo) y no le quedan mas usos
    Assertions.assertTrue(tarjeta2.getUsosPorDia() == 4);
    Assertions.assertFalse(tarjeta2.puedeUsar());

    // Tiene 4 usos por dia (persona sin menores a cargo), no le quedan mas usos y no se le reinician xq no es media noche
    Assertions.assertFalse(tarjeta3.getUsosEnElDia().equals(tarjeta3.getUsosPorDia()));
  }

  @Test
  @DisplayName("Uso tarjeta en una heladera")
  public void registrarUsoTarjeta() {

    persona1 = PersonaVulnerable.with(1);

    tarjeta1 = TarjetaPersonaVulnerable.with(persona1, 5);
    tarjeta1.calcularUsosTarjeta(tarjeta1);

    heladera1 = Heladera.with(8);

    tarjeta1.registrarUsoTarjeta(heladera1);

    Assertions.assertTrue(tarjeta1.getRegistro().size() == 1);
  }
}

