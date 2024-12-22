package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class ColaboradorRouter implements IRouter {

  @Override
  public void apply(RouterConfig config) {
    config.apiBuilder(() -> {

      path("/signup", () -> {
        get(ServiceLocator.instanceOf(ColaboradorController.class)::create, TipoRol.GUEST);

        get("/humana", ctx -> ctx.render("signs/signHumana.hbs"), TipoRol.GUEST);
        post("/humana", ServiceLocator.instanceOf(ColaboradorController.class)::saveHumana, TipoRol.GUEST);

        get("/juridica", ctx -> ctx.render("signs/signJuridica.hbs"), TipoRol.GUEST);
        post("/juridica", ServiceLocator.instanceOf(ColaboradorController.class)::saveJuridica, TipoRol.GUEST);
      });

      path("/perfil", () ->
          get(ServiceLocator.instanceOf(ColaboradorController.class)::getProfile, TipoRol.COLABORADOR)
      );

      path("/colaboradores", () -> {
        get(ServiceLocator.instanceOf(ColaboradorController.class)::index, TipoRol.ADMIN);
        post(ctx -> ctx.result("POST Colaboradores"));

        path("/{id}", () -> {

          get("/edit", ctx -> ctx.result("GET Form edit"));

          path("/formas-de-colaboracion", () -> {
            get(ServiceLocator.instanceOf(ColaboradorController.class)::editFormasDeColaborar, TipoRol.COLABORADOR);
            post(ServiceLocator.instanceOf(ColaboradorController.class)::updateFormasDeColaborar, TipoRol.COLABORADOR);
          });

        });
      });
    });
  }
}
