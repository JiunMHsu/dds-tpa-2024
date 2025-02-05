package ar.edu.utn.frba.dds.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de Usuario.
 */
@Getter
@AllArgsConstructor
public class UsuarioDTO {
  private final String id;
  private final String nombre;
  private final String contrasenia;
  private final String email;
  private final String rol;
}
