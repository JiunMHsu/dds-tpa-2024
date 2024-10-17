package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.config.JavalinConfig;

// TODO - definir las rutas y permisos para heladeras
public class HeladeraRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() ->
                path("/heladeras", () -> {
                    get(ctx -> ctx.render("heladeras/heladeras.hbs"));
                    post(ctx -> ctx.result("OPERACION EXITOSA"));

                    path("/new", () -> get(ctx -> ctx.render("heladeras/heladera_crear.hbs")));
                    path("/{id}", () -> get(ctx -> ctx.render("heladeras/heladera_detalle.hbs")));
                })
        );
    }
}
