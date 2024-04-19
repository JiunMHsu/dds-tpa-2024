
package validador;

import java.io.IOException; /** para esFuerte */
import java.nio.file.Files; /** para esFuerte */
import java.nio.file.Paths; /** para esFuerte */
import java.util.List; /** para esFuerte */

/**
 * Validador de contraseñas según las recomendaciones
 * de la Sección 5.1.1.2 para Secretos Memorizados de la Guía NIST 800-63.
 * <p/>
 * Véase: <a href="https://pages.nist.gov/800-63-3/sp800-63b.html#memsecret"> Memorized Secret Verifiers </a>
 */

public class ValidadorDeContrasenias {

  // TODO - esValida()
  public boolean esValida(String unaClave) {
    return true;
  }

  // TODO - tieneMasDeOchoCaracteres()
  private boolean tieneMasDeOchoCaracteres(String unaClave) {
    return true;
  }

  private boolean esFuerte(String unaClave) throws IOException {
    List <String> peoresContrasenias;
    peoresContrasenias = Files.readAllLines(Paths.get("contraseniasComunes.txt"));
    return !peoresContrasenias.contains(unaClave);
  }

  private boolean noTieneCaracteresRepetidosConsecutivos(String unaClave) {
    for (int i = 0; i < unaClave.length(); i++){
      if (unaClave.charAt(i) == unaClave.charAt(i + 1) && unaClave.charAt(i) == unaClave.charAt(i + 2)){
        return  false;
      }
    }
    return true;
  }

  // TODO - esDesordenadoAlfabeticamente()
  private boolean esDesordenadoAlfabeticamente(String unaClave) {
    // si tiene secuencias tipo abc, fgh, cba, no va
    return true;
  }

  // TODO - esDesordenadoNumericamente()
  private boolean esDesordenadoNumericamente(String unaClave) {
    // si tiene secuencias tipo 123, 345, 876, no va
    return true;
  }
}
