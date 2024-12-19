package ar.edu.utn.frba.dds.models.entities.rol;

import io.javalin.security.RouteRole;

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
