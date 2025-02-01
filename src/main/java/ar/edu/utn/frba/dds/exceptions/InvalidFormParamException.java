package ar.edu.utn.frba.dds.exceptions;

/**
 * Excepción que se lanza al validar un formulario y éste cuenta con parámetros inválidos.
 */
public class InvalidFormParamException extends RuntimeException {
  public InvalidFormParamException(String message) {
    super(message);
  }

  public InvalidFormParamException() {
    super();
  }
}
