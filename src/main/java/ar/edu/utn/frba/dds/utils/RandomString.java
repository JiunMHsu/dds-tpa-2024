package ar.edu.utn.frba.dds.utils;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

/**
 * Generate a random string.
 */
public class RandomString {

  private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String lower = upper.toLowerCase();
  private static final String digits = "0123456789";
  private static final String alphanum = upper + lower + digits;
  private final Random random;
  private final char[] symbols;
  private final char[] buf;

  /**
   * Crea un generador de cadenas alfanuméricas.
   *
   * @param length  longitud de la cadena generada
   * @param random  instancia de Random para la generación de números aleatorios
   * @param symbols conjunto de símbolos permitidos en la cadena generada
   */
  public RandomString(int length, Random random, String symbols) {
    if (length < 1) {
      throw new IllegalArgumentException();
    }

    if (symbols.length() < 2) {
      throw new IllegalArgumentException();
    }

    this.random = Objects.requireNonNull(random);
    this.symbols = symbols.toCharArray();
    this.buf = new char[length];
  }

  /**
   * Crea un generador de cadenas alfanuméricas.
   *
   * @param length longitud de la cadena generada
   * @param random instancia de Random para la generación de números aleatorios
   */
  public RandomString(int length, Random random) {
    this(length, random, alphanum);
  }

  /**
   * Crea un generador de cadenas alfanuméricas utilizando un generador seguro.
   *
   * @param length longitud de la cadena generada
   */
  public RandomString(int length) {
    this(length, new SecureRandom());
  }

  /**
   * Create session identifiers.
   */
  public RandomString() {
    this(21);
  }

  /**
   * Genera una cadena aleatoria.
   *
   * @return una cadena aleatoria generada
   */
  public String nextString() {
    for (int idx = 0; idx < buf.length; ++idx) {
      buf[idx] = symbols[random.nextInt(symbols.length)];
    }
    return new String(buf);
  }
}
