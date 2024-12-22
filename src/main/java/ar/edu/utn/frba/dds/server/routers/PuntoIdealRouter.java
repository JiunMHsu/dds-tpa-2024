package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoIdealController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class PuntoIdealRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() -> path("/punto-ideal", () ->
        post(ServiceLocator.instanceOf(PuntoIdealController.class)::create, TipoRol.ADMIN))
    );
  }
}
