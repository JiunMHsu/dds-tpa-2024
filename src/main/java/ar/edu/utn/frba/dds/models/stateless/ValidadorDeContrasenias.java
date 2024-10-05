package ar.edu.utn.frba.dds.models.stateless;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Validador de contraseñas según las recomendaciones
 * de la Sección 5.1.1.2 para Secretos Memorizados de la Guía NIST 800-63.
 * <p/>
 * Véase: <a href="https://pages.nist.gov/800-63-3/sp800-63b.html#memsecret"> Memorized Secret Verifiers </a>
 */
public class ValidadorDeContrasenias {
    public boolean esValida(String unaClave) {
        return this.tieneMasDeOchoCaracteres(unaClave)
                && this.esFuerte(unaClave)
                && this.noTieneCaracteresRepetidosConsecutivos(unaClave)
                && this.esDesordenadoAscendentemente(unaClave)
                && this.esDesordenadoDescendentemente(unaClave);
    }

    private boolean tieneMasDeOchoCaracteres(String unaClave) {
        return unaClave.length() >= 8;
    }

    private boolean esFuerte(String unaClave) {
        try {
            List<String> peoresContrasenias = Files.readAllLines(Paths.get("src/main/resources/contraseniasComunes.txt"));
            return !peoresContrasenias.contains(unaClave);
        } catch (IOException error) {
            error.printStackTrace();
        }
        return false;
    }

    private boolean noTieneCaracteresRepetidosConsecutivos(String unaClave) {
        for (int i = 0; i < unaClave.length() - 2; i++) {
            char actualCaracter = unaClave.charAt(i);
            char siguienteCaracter = unaClave.charAt(i + 1);
            char siguienteSiguienteCaracter = unaClave.charAt(i + 2);
            if (actualCaracter == siguienteCaracter && actualCaracter == siguienteSiguienteCaracter) {
                return false;
            }
        }
        return true;
    }

    private boolean esDesordenadoAscendentemente(String unaClave) {
        for (int i = 0; i < unaClave.length() - 2; i++) {
            char actualCaracter = unaClave.charAt(i);
            char siguienteCaracter = unaClave.charAt(i + 1);
            char siguienteSiguienteCaracter = unaClave.charAt(i + 2);
            if (siguienteCaracter == actualCaracter + 1 && siguienteSiguienteCaracter == siguienteCaracter + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean esDesordenadoDescendentemente(String unaClave) {
        String cadenaAlReves = new StringBuffer(unaClave).reverse().toString();
        return this.esDesordenadoAscendentemente(cadenaAlReves);
    }
}
