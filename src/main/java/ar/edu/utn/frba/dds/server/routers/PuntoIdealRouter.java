package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoIdealController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.JavalinConfig;

public class PuntoIdealRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() -> path("/punto-ideal", () ->
                post(ServiceLocator.instanceOf(PuntoIdealController.class)::create, TipoRol.ADMIN))
        );
    }
}
