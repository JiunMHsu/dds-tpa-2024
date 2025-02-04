package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.reporte.ReporteController;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import io.javalin.config.RouterConfig;

/**
 * Router de reportes.
 */
public class ReporteRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() ->
        path("/reportes", () -> {
          get(ServiceLocator.instanceOf(ReporteController.class)::index,
              TipoRol.ADMIN,
              TipoRol.COLABORADOR);

          post(ServiceLocator.instanceOf(ReporteController.class)::generate,
              TipoRol.ADMIN,
              TipoRol.COLABORADOR);

          get("/{id}/*", ServiceLocator.instanceOf(ReporteController.class)::show,
              TipoRol.ADMIN,
              TipoRol.COLABORADOR);
        })
    );
  }
}
