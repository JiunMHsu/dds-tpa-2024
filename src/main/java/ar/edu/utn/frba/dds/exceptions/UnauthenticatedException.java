package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta realizar una acción sin autenticarse.
 */
public class UnauthenticatedException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public UnauthenticatedException(String message) {
    super(message);
  }
}
