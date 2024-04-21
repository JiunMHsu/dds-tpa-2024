package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import validador.ValidadorDeContrasenias;
public class testValidador {

    ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
    @Test
    @DisplayName("Una clave es fuerte si no pertenece a las 10000 claves debiles")
    public void esFuerte() {
        String unaClaveDebil = "dragon";
        String unaClaveFuerte = "gloria";
        Assertions.assertFalse(validador.esFuerte(unaClaveDebil));
        Assertions.assertTrue(validador.esFuerte(unaClaveFuerte));
    }

    @Test
    @DisplayName("Una clave no tiene caracteres repetidos consecutivos cuando posee 3 o mas")
    public void noTieneCaracteresRepetidosConsecutivos(){
        String claveCaracRepetConsec = "fraaaa";
    }
}
