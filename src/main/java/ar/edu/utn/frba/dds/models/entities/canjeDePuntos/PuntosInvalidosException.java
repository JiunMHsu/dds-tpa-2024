package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

/**
 * Excepción que se lanza cuando se intenta realizar un canje
 * de puntos con una cantidad de puntos inválida.
 */
public class PuntosInvalidosException extends RuntimeException {
  public PuntosInvalidosException() {
    super();
  }
}
