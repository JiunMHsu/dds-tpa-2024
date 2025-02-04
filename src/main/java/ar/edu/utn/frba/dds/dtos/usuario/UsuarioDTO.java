package ar.edu.utn.frba.dds.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Create Usuario DTO.
 */
@Getter
@AllArgsConstructor
@Builder
public class UsuarioDTO {
  private final String nombre;
  private final String contrasenia;
  private final String email;
  private final String rol;

  public static UsuarioDTO con(String nombre,
                               String contrasenia,
                               String email,
                               String rol) {
    return UsuarioDTO.builder()
        .nombre(nombre)
        .contrasenia(contrasenia)
        .email(email)
        .rol(rol)
        .build();
  }
}
