package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import io.javalin.Javalin;

public class UnauthorizedHandler implements IHandler {
  @Override
  public void setHandler(Javalin app) {
    app.exception(UnauthorizedException.class, (e, ctx) -> {
      System.out.println("Error de autorizacion. Error: " + e.getMessage());
      ctx.status(401); // en realidad, es un 403, por falta de permisos
      ctx.render("unauthorized.hbs");
    });
  }
}
