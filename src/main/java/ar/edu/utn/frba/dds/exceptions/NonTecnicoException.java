package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta recuperar un técnico de un usuario que no es técnico.
 */
public class NonTecnicoException extends RuntimeException {

  /**
   * Constructor.
   */
  public NonTecnicoException() {
    super("El usuario no es un técnico.");
  }
}
