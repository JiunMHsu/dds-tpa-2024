package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza al validar un formulario y éste cuenta con parámetros inválidos.
 */
public class InvalidFormParamException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje de error.
   */
  public InvalidFormParamException(String message) {
    super(message);
  }

  /**
   * Constructor.
   */
  public InvalidFormParamException() {
    super();
  }
}
