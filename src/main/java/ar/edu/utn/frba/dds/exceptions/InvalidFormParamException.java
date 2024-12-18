package ar.edu.utn.frba.dds.exceptions;

public class InvalidFormParamException extends RuntimeException {
  public InvalidFormParamException(String message) {
    super(message);
  }

  public InvalidFormParamException() {
    super();
  }
}
