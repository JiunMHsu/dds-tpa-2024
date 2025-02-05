package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta suscribir a una heladera llena.
 * TODO: Revisar Propósito
 */
public class SuscripcionHeladeraLlenaException extends Throwable {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public SuscripcionHeladeraLlenaException(String message) {
    super(message);
  }
}
