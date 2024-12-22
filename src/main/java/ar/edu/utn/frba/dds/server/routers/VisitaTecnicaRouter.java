package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.tecnico.VisitaTecnicaController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class VisitaTecnicaRouter implements IRouter {
  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() ->
        path("/visitas-tecnicas", () -> {
          get(ServiceLocator.instanceOf(VisitaTecnicaController.class)::index, TipoRol.TECNICO);
          post(ServiceLocator.instanceOf(VisitaTecnicaController.class)::save, TipoRol.TECNICO);

          get("/new", ServiceLocator.instanceOf(VisitaTecnicaController.class)::create, TipoRol.TECNICO);
          get("/{id}", ServiceLocator.instanceOf(VisitaTecnicaController.class)::show, TipoRol.ADMIN, TipoRol.TECNICO);
        })
    );
  }
}
