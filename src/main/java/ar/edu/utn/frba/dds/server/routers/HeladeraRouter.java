package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class HeladeraRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() ->
                path("/heladeras", () -> {
                    get(ServiceLocator.instanceOf(HeladeraController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN);
                    post(ServiceLocator.instanceOf(HeladeraController.class)::save, TipoRol.ADMIN);

                    get("/new", ServiceLocator.instanceOf(HeladeraController.class)::create, TipoRol.ADMIN);

                    path("/{id}", () -> {
                        get(ServiceLocator.instanceOf(HeladeraController.class)::show, TipoRol.ADMIN, TipoRol.COLABORADOR);
                        post(ServiceLocator.instanceOf(HeladeraController.class)::update, TipoRol.ADMIN, TipoRol.COLABORADOR);

                        get("/edit", ServiceLocator.instanceOf(HeladeraController.class)::edit, TipoRol.ADMIN, TipoRol.COLABORADOR);
                    });
                })
        );
    }
}
