
package validador;

import java.io.IOException; /** para esFuerte */
import java.nio.file.Files; /** para esFuerte */
import java.nio.file.Paths; /** para esFuerte */
import java.util.List;      /** para esFuerte */

/**
 * Validador de contraseñas según las recomendaciones
 * de la Sección 5.1.1.2 para Secretos Memorizados de la Guía NIST 800-63.
 * <p/>
 * Véase: <a href="https://pages.nist.gov/800-63-3/sp800-63b.html#memsecret"> Memorized Secret Verifiers </a>
 */

public class ValidadorDeContrasenias {

  // TODO - esValida()
  public boolean esValida(String unaClave) {
    return tieneMasDeOchoCaracteres(unaClave) && esFuerte(unaClave) && noTieneCaracteresRepetidosConsecutivos(unaClave)
              && esDesordenadoAscendentemente(unaClave) && esDesordenadoDescendentemente(unaClave);
  }

  // TODO - tieneMasDeOchoCaracteres()
  private boolean tieneMasDeOchoCaracteres(String unaClave) {
    return unaClave.length() > 8;
  }

  private boolean esFuerte(String unaClave) {
    List <String> peoresContrasenias = List.of();
    try {
      peoresContrasenias = Files.readAllLines(Paths.get("contraseniasComunes.txt"));
    } catch (IOException error) {
      error.printStackTrace();
    }
    return !peoresContrasenias.contains(unaClave);
  }

  private boolean noTieneCaracteresRepetidosConsecutivos(String unaClave) {
    for (int i = 0; i < unaClave.length() - 1; i++){
      if (unaClave.charAt(i) == unaClave.charAt(i + 1) && unaClave.charAt(i) == unaClave.charAt(i + 2)){
        return  false;
      }
    }
    return true;
  }

  // TODO - esDesordenadoAscendentemente()
  private boolean esDesordenadoAscendentemente(String unaClave) {
    // si tiene secuencias tipo abc, fgh, 123 no va
    for (int i = 0; i < unaClave.length() - 1; i++) { //ascendentemente - si ya dos caracteres no lo estan,
      if(unaClave.charAt(i) >= unaClave.charAt(i + 1))   //como abd, va a ser verdadero. lo mismo con los numeros
        return true;                                  //porque son caracteres ascii
    }
    return false;
  }

  // TODO - esDesordenadoDescendentemente()
  private boolean esDesordenadoDescendentemente(String unaClave) {
    // si tiene secuencias tipo 321, cba, hgf no va
    for(int i = 0; i < unaClave.length() - 1; i++) {  // lo mismo de arriba
      if(unaClave.charAt(i) <= unaClave.charAt(i + 1))
        return true;
    }
    return false;
  }
}
