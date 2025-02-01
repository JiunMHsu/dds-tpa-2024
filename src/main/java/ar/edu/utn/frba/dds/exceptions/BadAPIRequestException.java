package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se realiza una petición inválida a la API.
 */
public class BadAPIRequestException extends RuntimeException {
  public BadAPIRequestException(String message) {
    super(message);
  }
}
