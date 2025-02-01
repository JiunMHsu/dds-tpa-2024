package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoDeColocacionController;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import io.javalin.config.RouterConfig;

public class PuntoIdealRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() -> path("/punto-ideal", () ->
        post(ServiceLocator.instanceOf(PuntoDeColocacionController.class)::create, TipoRol.ADMIN))
    );
  }
}
