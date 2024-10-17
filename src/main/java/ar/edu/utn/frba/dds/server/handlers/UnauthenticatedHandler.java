package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import io.javalin.Javalin;

public class UnauthenticatedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(UnauthenticatedException.class, (e, ctx) -> {
            ctx.status(401);
            ctx.redirect("/login");
        });
    }
}
