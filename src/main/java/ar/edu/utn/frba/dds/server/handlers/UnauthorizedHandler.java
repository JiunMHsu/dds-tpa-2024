package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import io.javalin.Javalin;

public class UnauthorizedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(UnauthorizedException.class, (e, ctx) -> {
            ctx.status(401);
            ctx.render("unauthorized.hbs");
        });
    }
}
