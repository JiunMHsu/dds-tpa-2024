package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.tecnico.VisitaTecnicoController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class VisitaTecnicoRouter implements IRouter {
  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() ->
        path("/visitas-tecnico", () -> {
          post(ServiceLocator.instanceOf(VisitaTecnicoController.class)::save, TipoRol.TECNICO);
          
          get("/new", ServiceLocator.instanceOf(VisitaTecnicoController.class)::create, TipoRol.TECNICO);
          get("/{id}", ServiceLocator.instanceOf(VisitaTecnicoController.class)::show, TipoRol.ADMIN, TipoRol.TECNICO);
        })
    );
  }
}
