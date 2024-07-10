package ar.edu.utn.frba.dds.validador;

public class ValidadorDeMail {

  // TODO
  // Hacer la validación de verdad
  public static Boolean esValido(String email) {
    return email != null && email.contains("@");
  }
}
