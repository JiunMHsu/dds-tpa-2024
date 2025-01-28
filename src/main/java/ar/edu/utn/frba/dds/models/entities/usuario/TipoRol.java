package ar.edu.utn.frba.dds.models.entities.usuario;

import io.javalin.security.RouteRole;

/**
 * Enumerado Tipo Rol.
 */
public enum TipoRol implements RouteRole {
  GUEST,
  ADMIN,
  COLABORADOR,
  TECNICO;

  public boolean isAdmin() {
    return this == ADMIN;
  }

  public boolean isColaborador() {
    return this == COLABORADOR;
  }

  public boolean isTecnico() {
    return this == TECNICO;
  }
}
