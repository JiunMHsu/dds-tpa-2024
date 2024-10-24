package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.tecnico.TecnicoController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class TecnicoRouter implements IRouter { // TODO - ver si esta bn el enrutado
    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() -> {
            path("/tecnicos", () -> {
                post(ServiceLocator.instanceOf(TecnicoController.class)::save, TipoRol.ADMIN);

                get(ServiceLocator.instanceOf(TecnicoController.class)::index, TipoRol.ADMIN);

                path("/{id}", () -> {
                    get(ServiceLocator.instanceOf(TecnicoController.class)::show, TipoRol.ADMIN);
                });

                get("/new", ServiceLocator.instanceOf(TecnicoController.class)::save, TipoRol.ADMIN);
            });

        });

    }
}

