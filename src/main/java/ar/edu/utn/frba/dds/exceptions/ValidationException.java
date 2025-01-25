package ar.edu.utn.frba.dds.exceptions;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
