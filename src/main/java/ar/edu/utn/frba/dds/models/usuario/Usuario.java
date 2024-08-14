package ar.edu.utn.frba.dds.models.usuario;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Usuario {

  private String nombre;
  private String contrasenia;
  private String email;

  public static Usuario with(String nombreUsuario, String contrasenia, String email) {
    return Usuario
        .builder()
        .nombre(nombreUsuario)
        .contrasenia(contrasenia)
        .email(email)
        .build();
  }

  public static Usuario withEmail(String email) {
    return Usuario
        .builder()
        .email(email)
        .build();
  }

  public static Usuario empty() {
    return Usuario
        .builder()
        .build();
  }
}