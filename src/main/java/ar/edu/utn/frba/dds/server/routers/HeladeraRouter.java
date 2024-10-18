package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import io.javalin.config.JavalinConfig;

// TODO - definir las rutas y permisos para heladeras
public class HeladeraRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() ->
                path("/heladeras", () -> {
                    get(ServiceLocator.instanceOf(HeladeraController.class)::index);
                    post(ctx -> ctx.result("OPERACION EXITOSA"));

                    get("/new", ServiceLocator.instanceOf(HeladeraController.class)::create);
                    get("/{id}/edit", ServiceLocator.instanceOf(HeladeraController.class)::edit);
                    get("/{id}", ServiceLocator.instanceOf(HeladeraController.class)::show);
                })
        );
    }
}
