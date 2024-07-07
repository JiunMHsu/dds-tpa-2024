package ar.edu.utn.frba.dds.models.tarjeta;

import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneradorDeCodigo {

  public static String generadorCodigo() {
    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    StringBuilder codigoBuilder = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      int indiceAleatorio = GeneradorDeCodigo.numeroAleatorioEnRango(0, banco.length() - 1);
      char caracterAleatorio = banco.charAt(indiceAleatorio);
      codigoBuilder.append(caracterAleatorio);
    }

    return codigoBuilder.toString();
  }

  private static int numeroAleatorioEnRango(int minimo, int maximo) {
    return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
  }
}
