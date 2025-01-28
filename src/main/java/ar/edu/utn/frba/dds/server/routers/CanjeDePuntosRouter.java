package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.canjeDePuntos.CanjeDePuntosController;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import io.javalin.config.RouterConfig;

public class CanjeDePuntosRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() ->
        path("/canje-de-puntos", () -> {
          get("/new", ServiceLocator.instanceOf(CanjeDePuntosController.class)::create, TipoRol.COLABORADOR);
          post(ServiceLocator.instanceOf(CanjeDePuntosController.class)::save, TipoRol.COLABORADOR);

          get("/historial", ServiceLocator.instanceOf(CanjeDePuntosController.class)::index, TipoRol.COLABORADOR);
        }));
  }
}
