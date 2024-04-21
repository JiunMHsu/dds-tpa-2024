package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import validador.ValidadorDeContrasenias;
public class testValidador {

    ValidadorDeContrasenias validador = new ValidadorDeContrasenias();
    @Test
    public void unaClaveEsFuerteSiNoPerteneceALas10000Debiles() {
        String unaClaveDebil = "dragon";
        String unaClaveFuerte = "gloria";
        Assertions.assertFalse(validador.esFuerte(unaClaveDebil));
        Assertions.assertTrue(validador.esFuerte(unaClaveFuerte));
    }

    @Test
    public void unaClaveNoTieneCaracteresRepetidosConsecutivos(){
        String unaClaveCaracteresRePetidosConsecutivos = "fraaaa";
    }
}
