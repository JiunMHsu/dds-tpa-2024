package ar.edu.utn.frba.dds.exceptions;

public class BadAPIRequestException extends RuntimeException {
  public BadAPIRequestException(String message) {
    super(message);
  }
}
