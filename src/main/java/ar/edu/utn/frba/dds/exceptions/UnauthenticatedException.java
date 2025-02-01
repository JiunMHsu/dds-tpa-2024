package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta realizar una acción sin autenticarse.
 */
public class UnauthenticatedException extends RuntimeException {
  public UnauthenticatedException(String message) {
    super(message);
  }
}
