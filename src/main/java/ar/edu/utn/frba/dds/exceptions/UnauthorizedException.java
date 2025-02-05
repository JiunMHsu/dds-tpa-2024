package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta realizar una acción sin autorización.
 */
public class UnauthorizedException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public UnauthorizedException(String message) {
    super(message);
  }

  /**
   * Constructor.
   */
  public UnauthorizedException() {
    super();
  }

}
