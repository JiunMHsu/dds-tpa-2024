package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.Incidente.AlertaController;
import io.javalin.config.RouterConfig;

public class IncidenteRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() ->
                path("/alertas", () -> {
                    get(ServiceLocator.instanceOf(AlertaController.class)::index);

                    path("/{id}", () -> {
                        get(ServiceLocator.instanceOf(AlertaController.class)::show);
                    });
                })
        );
    }
}
