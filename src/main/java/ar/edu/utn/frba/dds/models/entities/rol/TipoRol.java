package ar.edu.utn.frba.dds.models.entities.rol;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMIN,
    COLABORADOR,
    TECNICO,
}
