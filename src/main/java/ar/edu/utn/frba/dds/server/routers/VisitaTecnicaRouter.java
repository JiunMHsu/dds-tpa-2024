package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.tecnico.VisitaTecnicaController;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import io.javalin.config.RouterConfig;

/**
 * Router de visita técnica.
 */
public class VisitaTecnicaRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() ->
        path("/visitas-tecnicas", () -> {
          get(ServiceLocator.instanceOf(VisitaTecnicaController.class)::index,
              TipoRol.ADMIN,
              TipoRol.TECNICO);

          post(ServiceLocator.instanceOf(VisitaTecnicaController.class)::save,
              TipoRol.TECNICO);

          get("/new", ServiceLocator.instanceOf(VisitaTecnicaController.class)::create,
              TipoRol.TECNICO);
        })
    );
  }
}
