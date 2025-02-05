package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando una suscripción no tiene viandas.
 * TODO: Revisar Propósito
 */
public class SuscripcionFaltaViandaException extends Throwable {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public SuscripcionFaltaViandaException(String message) {
    super(message);
  }
}
