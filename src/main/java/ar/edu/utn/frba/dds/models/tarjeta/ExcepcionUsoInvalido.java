package ar.edu.utn.frba.dds.models.tarjeta;

public class ExcepcionUsoInvalido extends Throwable {
  public ExcepcionUsoInvalido(String mensaje) {
    super(mensaje);
  }
}
