package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tarjeta.RegistroTarjetas;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.tarjeta.GeneradorDeCodigo;

import ar.edu.utn.frba.dds.models.usuario.PersonaVulnerable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TestTarjetas {

    private Tarjeta tarjeta1;
    private Tarjeta tarjeta2;
    private Tarjeta tarjeta3;
    private List<Tarjeta> tarjetasExistentes;


    private PersonaVulnerable persona1;
    private PersonaVulnerable persona2;
    private PersonaVulnerable persona3;

    private Heladera heladera1;
    private Heladera heladera2;

    private LocalTime mediaNoche = LocalTime.of(0, 0);

    @Test
    @DisplayName("Se generan codigos no repetidos")
    public void comprobarCodigosRepetidos() {
        tarjeta1 = new Tarjeta("1af56789d87", null, null, null, null, null);
        tarjeta2 = new Tarjeta("82df498ad23", null, null, null, null, null);
        tarjeta3 = new Tarjeta("12d34r325qw", null, null, null, null, null);

        tarjetasExistentes = new ArrayList<>();
        tarjetasExistentes.add(tarjeta1);
        tarjetasExistentes.add(tarjeta2);
        tarjetasExistentes.add(tarjeta3);

        GeneradorDeCodigo generadorDeCodigo = new GeneradorDeCodigo();
        generadorDeCodigo.setTarjetasRegistradas(tarjetasExistentes);
        String nuevoCodigo = generadorDeCodigo.generadorCodigo(generadorDeCodigo.getTarjetasRegistradas());

        Assertions.assertFalse(tarjetasExistentes.stream().anyMatch(tarjeta -> tarjeta.getCodigo().equals(nuevoCodigo)),
                "El código generado ya existe en la lista de tarjetas existentes.");

    }

    @Test
    @DisplayName("Usos de la tarjeta")
    public void puedeUsar() {

        persona1 = new PersonaVulnerable();
        persona2 = new PersonaVulnerable();
        persona3 = new PersonaVulnerable();

        persona1.setMenoresACargo(1);
        persona2.setMenoresACargo(0);
        persona3.setMenoresACargo(0);

        tarjeta1 = new Tarjeta(null, persona1, 5, null, null, null);
        tarjeta1.calcularUsosTarjeta(tarjeta1);

        tarjeta2 = new Tarjeta(null, persona2, 0, null, null, null);
        tarjeta2.calcularUsosTarjeta(tarjeta2);

        tarjeta3 = new Tarjeta(null, persona3, 0, null, mediaNoche, null);
        tarjeta3.calcularUsosTarjeta(tarjeta3);
        tarjeta3.verificarMedianoche();

        // Tiene 6 usos por dia (persona con 1 menor a cargo) y le quedan 5 por usar
        Assertions.assertTrue(tarjeta1.getUsosPorDia() == 6);
        Assertions.assertTrue(tarjeta1.puedeUsar());

        // Tiene 4 usos por dia (persona sin menores a cargo) y no le quedan mas usos
        Assertions.assertTrue(tarjeta2.getUsosPorDia() == 4);
        Assertions.assertFalse(tarjeta2.puedeUsar());

        // Tiene 4 usos por dia (persona sin menores a cargo), no le quedan mas usos y no se le reinician xq no es media noche
        Assertions.assertFalse(tarjeta3.getUsosDia() == tarjeta3.getUsosPorDia());
    }

    /* @Test
    @DisplayName("Uso tarjeta en una heladera")
    public void registrarUsoTarjeta(){

        persona1 = new PersonaVulnerable();
        persona1.setMenoresACargo(1);

        tarjeta1 = new Tarjeta(null, persona1, 5, null, null, null);
        tarjeta1.calcularUsosTarjeta(tarjeta1);

        heladera1 = new Heladera("Pancho", null, null, null, null, null, true);

        tarjeta1.registrarUsoTarjeta(heladera1);

        Assertions.assertTrue(tarjeta1.getUsosDia() == 4);
    } */

}

