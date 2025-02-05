package ar.edu.utn.frba.dds.models.stateless;

import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase Generador de Credenciales.
 */
public class GeneradorDeCredenciales {

  /**
   * Genera un usuario con nombre y mail.
   *
   * @param nombre Nombre del usuario.
   * @param mail   Mail del usuario.
   * @return Usuario.
   */
  public static Usuario generarUsuario(String nombre, String mail) {
    return Usuario.con(nombre, GeneradorDeCredenciales.generarContrasenia(), mail, TipoRol.COLABORADOR);
  }

  private static String generarContrasenia() {
    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder contraseniaUsuario = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      int indiceAleatorio = numeroAleatorioEnRango(banco.length() - 1);
      char caracterAleatorio = banco.charAt(indiceAleatorio);
      contraseniaUsuario.append(caracterAleatorio);
    }

    return contraseniaUsuario.toString();
  }

  private static int numeroAleatorioEnRango(int maximo) {
    return ThreadLocalRandom.current().nextInt(0, maximo + 1);
  }
}
