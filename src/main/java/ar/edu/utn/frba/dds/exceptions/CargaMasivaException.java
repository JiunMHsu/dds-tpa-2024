package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza cuando ocurre un error en la carga masiva de datos.
 */
public class CargaMasivaException extends RuntimeException {
  public CargaMasivaException(String message) {
    super(message);
  }
}
