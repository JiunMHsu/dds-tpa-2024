package ar.edu.utn.frba.dds.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorDeContraseniasTest {

    ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

    @Test
    @DisplayName("Una clave es fuerte si no pertenece a las 10000 claves debiles")
    public void esFuerte() {
        String unaClaveDebil = "sherlock";
        String unaClaveFuerte = "ogiuh3e4";

        Assertions.assertFalse(validador.esValida(unaClaveDebil));
        Assertions.assertTrue(validador.esValida(unaClaveFuerte));
    }

    @Test
    @DisplayName("Una clave no tiene caracteres repetidos consecutivos cuando posee 3 o mas")
    public void noTieneCaracteresRepetidosConsecutivos() {
        String claveCaracRepetConsec = "fraaaa&^h";
        String claveCaracNoRepetConsec = "awibciuawb";

        Assertions.assertFalse(validador.esValida(claveCaracRepetConsec));
        Assertions.assertTrue(validador.esValida(claveCaracNoRepetConsec));
    }

    @Test
    @DisplayName("Una clave debe tener mas de 8 caracteres")
    public void tieneMasDeOchoCaracteres() {
        String claveConMasDeOchoCaracteres = "fuerteElDragon";
        String claveConOchoCaracteres = "fuerteja";
        String claveConMenosDeOchoCaracteres = "fuerte";

        Assertions.assertTrue(validador.esValida(claveConMasDeOchoCaracteres));
        Assertions.assertTrue(validador.esValida(claveConOchoCaracteres));
        Assertions.assertFalse(validador.esValida(claveConMenosDeOchoCaracteres));
    }

    @Test
    @DisplayName("Una clave esta desordenada ascendentemente cuando tiene 3 caracteres ordenados segun su valor en el codigo ascii")
    public void esDesordenadoAscendentemente() {
        String claveDesordenada = "juakmju2";
        String claveOrdenada = "mabcjugq3e";

        Assertions.assertTrue(validador.esValida(claveDesordenada));
        Assertions.assertFalse(validador.esValida(claveOrdenada));
    }

    @Test
    @DisplayName("Una clave esta desordenada descendentemente cuando tiene 3 caracteres ordenados según su valor en el codigo ascii")
    public void esDesordenadoDescendentemente() {
        String claveDesordenada = "juakmju249";
        String claveOrdenada = "mcbaju*0";

        Assertions.assertTrue(validador.esValida(claveDesordenada));
        Assertions.assertFalse(validador.esValida(claveOrdenada));
    }
}
