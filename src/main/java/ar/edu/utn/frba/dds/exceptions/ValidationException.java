package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se produce un error de validación.
 */
public class ValidationException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public ValidationException(String message) {
    super(message);
  }
}
