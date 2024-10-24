package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import io.javalin.Javalin;

public class UnauthenticatedHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(UnauthenticatedException.class, (e, ctx) -> {
            System.out.println("usuario no autenticado");
            String forwardTo = ctx.path().equals("/login")
                    ? ""
                    : "?forward=" + ctx.path();

            System.out.println(forwardTo);

            ctx.status(401);
            ctx.redirect("/login" + forwardTo);
        });
    }
}
