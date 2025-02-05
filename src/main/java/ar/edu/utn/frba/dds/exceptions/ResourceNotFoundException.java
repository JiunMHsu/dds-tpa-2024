package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando no se encuentra un recurso.
 */
public class ResourceNotFoundException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructor.
   */
  public ResourceNotFoundException() {
    super();
  }
}
