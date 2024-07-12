package ar.edu.utn.frba.dds.utils;

import java.util.concurrent.ThreadLocalRandom;

public class GeneradorDeCodigosTarjeta {

  public static String generar() {
    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder codigoBuilder = new StringBuilder();

    for (int i = 0; i < 10; i++) {
      int indiceAleatorio = GeneradorDeCodigosTarjeta.numeroAleatorioEnRango(0, banco.length() - 1);
      char caracterAleatorio = banco.charAt(indiceAleatorio);
      codigoBuilder.append(caracterAleatorio);
    }

    return codigoBuilder.toString();
  }

  private static int numeroAleatorioEnRango(int minimo, int maximo) {
    return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
  }
}
