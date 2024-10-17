package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.javalin.config.JavalinConfig;
import java.util.Arrays;

public class Routers {

    private static final IRouter[] routers = new IRouter[]{
            new ColaboracionRouter(),
            new HeladeraRouter(),
            new PuntoIdealRouter(),
            new SuscripcionRouter(),
            new ColaboradorRouter(),
            new IncidenteRouter(),
            new CanjeDePuntosRouter(),
            new ReporteRouter()
    };

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

        Arrays.stream(routers).forEach(router -> router.apply(config));
    }

}
