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
            path("/perfil", () -> {
                get(ServiceLocator.instanceOf(ColaboradorController.class)::getProfile, TipoRol.COLABORADOR);
            });
            path("/colaboradores", () -> {
                get(ctx -> ctx.result("GET Colaboradores"));
                post(ctx -> ctx.result("POST Colaboradores"));

                // get("/new", ctx -> ctx.result("FORM REGISTRO")); (es el sign up)
                path("/{id}", () -> {
                    get(ctx -> ctx.result("GET Colaborador " + ctx.pathParam("id")));
                    post(ctx -> ctx.result("POST Colaborador " + ctx.pathParam("id")));

                    get("/edit", ctx -> ctx.result("GET Form edit"));

                    path("/formas-de-colaboracion", () -> {
                        get(ServiceLocator.instanceOf(ColaboradorController.class)::editFormasDeColaborar, TipoRol.COLABORADOR, TipoRol.ADMIN);
                        post(ServiceLocator.instanceOf(ColaboradorController.class)::updateFormasDeColaborar, TipoRol.COLABORADOR, TipoRol.ADMIN);
                    });

                });
            });
        });
    }
}
