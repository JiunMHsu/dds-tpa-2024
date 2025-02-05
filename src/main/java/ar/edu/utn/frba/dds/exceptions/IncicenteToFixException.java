package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando se intenta arreglar un incidente que no necesita ser arreglado.
 */
public class IncicenteToFixException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public IncicenteToFixException(String message) {
    super(message);
  }
}
