package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta recuperar
 * un colaborador de un usuario que no es colaborador.
 */
public class NotColaboratorException extends RuntimeException {
  public NotColaboratorException(String message) {
    super(message);
  }
}
