package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se realiza una petición inválida a la API.
 */
//TODO check mayuscula
public class BadAPIRequestException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public BadAPIRequestException(String message) {
    super(message);
  }
}
