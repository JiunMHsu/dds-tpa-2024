package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.reporte.ReporteController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class ReporteRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() -> {
            path("/reportes", () -> {
            get(ServiceLocator.instanceOf(ReporteController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN);
                path("/{id}", () -> {
                    get(ServiceLocator.instanceOf(ReporteController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
                });
            });

            }
        );
    }
}
