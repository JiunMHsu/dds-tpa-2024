package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;

import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.JavalinConfig;

public class ColaboradorRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() ->{
              path("/perfil", () -> {
                get(ServiceLocator.instanceOf(ColaboradorController.class)::getProfile, TipoRol.COLABORADOR);
              });

              path("/colaboradores", () -> {
                get(ServiceLocator.instanceOf(ColaboradorController.class)::index);
                post(ServiceLocator.instanceOf(ColaboradorController.class)::save);

                path("/{id}", () -> {
                  get(ServiceLocator.instanceOf(ColaboradorController.class)::show);
                });
              });
            }
        );
    }
}
