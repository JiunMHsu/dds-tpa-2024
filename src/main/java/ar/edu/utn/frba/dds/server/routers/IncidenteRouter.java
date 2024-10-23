package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.Incidente.AlertaController;
import ar.edu.utn.frba.dds.controllers.Incidente.FallaTecnicaController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class IncidenteRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() -> {
            path("/alertas", () -> {
                get(ServiceLocator.instanceOf(AlertaController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);

                path("/{id}", () -> {
                    get(ServiceLocator.instanceOf(AlertaController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
                });
            });

            path("/fallas-tecnicas", () -> {
                post(ServiceLocator.instanceOf(FallaTecnicaController.class)::save, TipoRol.COLABORADOR);

                get("/new", ServiceLocator.instanceOf(FallaTecnicaController.class)::create, TipoRol.COLABORADOR);

            });

        });

    }

}
