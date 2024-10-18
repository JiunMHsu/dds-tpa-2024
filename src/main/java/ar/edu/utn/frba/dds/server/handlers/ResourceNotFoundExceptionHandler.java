package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import io.javalin.Javalin;

public class ResourceNotFoundExceptionHandler implements IHandler {
    @Override
    public void setHandler(Javalin app) {
        app.exception(ResourceNotFoundException.class, (e, ctx) -> {
            System.out.println(e.getMessage());
            ctx.status(404);
            ctx.render("notfound.hbs");
        });
    }
}
