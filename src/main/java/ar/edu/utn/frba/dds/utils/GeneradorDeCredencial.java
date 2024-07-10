package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import java.util.concurrent.ThreadLocalRandom;

public class GeneradorDeCredencial {

  public static int numeroAleatorioEnRango(int minimo, int maximo) {
    return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
  }

  public Usuario generCredencial(String mail) {

    String nombreProvicional = nombreAleatorio();
    String contraseniaProvicional = contraseniaAleatorio();

    Usuario nuevoUsuario = new Usuario(nombreProvicional, contraseniaProvicional, mail);

    return nuevoUsuario;
  }

  public String nombreAleatorio() {
    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    String nombreUsuario = "";

    for (int i = 0; i < 6; i++) {
      int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
      char caracterAleatorio = banco.charAt(indiceAleatorio);
      nombreUsuario += caracterAleatorio;
    }

    return nombreUsuario;
  }

  public String contraseniaAleatorio() {

    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    String contraseniaUsuario = "";

    for (int i = 0; i < 6; i++) {
      int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
      char caracterAleatorio = banco.charAt(indiceAleatorio);
      contraseniaUsuario += caracterAleatorio;
    }
    return contraseniaUsuario;
  }
}
