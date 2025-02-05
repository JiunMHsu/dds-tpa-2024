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
public class CreateUsuarioDTO {
  private final String nombre;
  private final String contrasenia;
  private final String email;
  private final String rol;

  /**
   * Builder de CreateUsuarioDTO.
   *
   * @param nombre      nombre del usuario
   * @param contrasenia contraseña del usuario
   * @param email       email del usuario
   * @param rol         rol del usuario
   * @return CreateUsuarioDTO
   */
  public static CreateUsuarioDTO con(String nombre,
                                     String contrasenia,
                                     String email,
                                     String rol) {
    return CreateUsuarioDTO.builder()
        .nombre(nombre)
        .contrasenia(contrasenia)
        .email(email)
        .rol(rol)
        .build();
  }
}
