package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.Incidente.AlertaController;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import io.javalin.config.JavalinConfig;

public class IncidenteRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() ->
            path("/alertas", () -> {
                get(ServiceLocator.instanceOf(AlertaController.class)::index);

                path("/{id}", () -> {
                    get(ServiceLocator.instanceOf(AlertaController.class)::show);
                });
            })
        );
    }
}
