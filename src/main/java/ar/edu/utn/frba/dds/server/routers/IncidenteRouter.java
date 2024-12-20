package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.incidente.AlertaController;
import ar.edu.utn.frba.dds.controllers.incidente.FallaTecnicaController;
import ar.edu.utn.frba.dds.controllers.incidente.IncidenteController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class IncidenteRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() -> {
      path("/incidentes", () -> {
        get(ServiceLocator.instanceOf(IncidenteController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
        get("/{id}", ServiceLocator.instanceOf(IncidenteController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
      });

      path("/alertas", () -> {
        get(ServiceLocator.instanceOf(AlertaController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
        get("/{id}", ServiceLocator.instanceOf(AlertaController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
      });

      path("/fallas-tecnicas", () -> {
        get(ServiceLocator.instanceOf(FallaTecnicaController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
        post(ServiceLocator.instanceOf(FallaTecnicaController.class)::save, TipoRol.COLABORADOR);

        get("/new", ServiceLocator.instanceOf(FallaTecnicaController.class)::create, TipoRol.COLABORADOR);
        get("/{id}", ServiceLocator.instanceOf(FallaTecnicaController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN, TipoRol.TECNICO);
      });

    });

  }

}
