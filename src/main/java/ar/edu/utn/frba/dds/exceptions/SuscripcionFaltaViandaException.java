package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando una suscripción no tiene viandas.
 * TODO: Revisar Propósito
 */
public class SuscripcionFaltaViandaException extends Throwable {
  public SuscripcionFaltaViandaException(String message) {
    super(message);
  }
}
