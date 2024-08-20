package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.usuario.Usuario;
import java.util.concurrent.ThreadLocalRandom;

public class GeneradorDeCredenciales {

  public static Usuario generarUsuario(String nombre, String mail) {
    return Usuario.with(nombre, GeneradorDeCredenciales.generarContrasenia(), mail);
  }

  private static String generarContrasenia() {
    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder contraseniaUsuario = new StringBuilder();

    for (int i = 0; i < 6; i++) {
      int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
      char caracterAleatorio = banco.charAt(indiceAleatorio);
      contraseniaUsuario.append(caracterAleatorio);
    }

    return contraseniaUsuario.toString();
  }

  private static int numeroAleatorioEnRango(int minimo, int maximo) {
    return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
  }
}
