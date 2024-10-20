package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.config.JavalinConfig;

public class ColaboradorRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() ->
                path("/colaboradores", () -> {
                    get(ctx -> ctx.result("GET Colaboradores"));
                    post(ctx -> ctx.result("POST Colaboradores"));

                    // get("/new", ctx -> ctx.result("FORM REGISTRO")); (es el sign up)
                    path("/{id}", () -> {
                        get(ctx -> ctx.result("GET Colaborador " + ctx.pathParam("id")));
                        post(ctx -> ctx.result("POST Colaborador " + ctx.pathParam("id")));

                        get("/edit", ctx -> ctx.result("GET Form edit"));

                        path("/formas-de-colaboracion", () -> {
                            post(ctx -> ctx.result("POST Formas de Colaboracion"));
                            get("/edit", ctx -> ctx.result("GET Form edit"));
                        });

                    });
                })
        );
    }
}
