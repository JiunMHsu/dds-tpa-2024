package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se produce un error de validación.
 */
public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
