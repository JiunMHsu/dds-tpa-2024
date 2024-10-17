package ar.edu.utn.frba.dds.server;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.server.routers.ColaboracionRouter;
import ar.edu.utn.frba.dds.server.routers.HeladeraRouter;
import io.javalin.config.JavalinConfig;

public class Router {

    // TEMP
    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public static void apply(JavalinConfig config) {
        config.router.apiBuilder(() -> {
            get("/", ctx -> ctx.redirect("/home"));
            get("/home", ctx -> ctx.render("home/home.hbs"));
            get("/test", ctx -> ctx.result("DDS TPA"));

            path("/login", () -> {
                get(new SessionController(usuarioRepository)::index);
                post(new SessionController(usuarioRepository)::create);
            });

            get("/image/{id}", ctx -> ctx.result("IMAGEN"));
        });

        HeladeraRouter.apply(config);
        ColaboracionRouter.apply(config);

    }

}
