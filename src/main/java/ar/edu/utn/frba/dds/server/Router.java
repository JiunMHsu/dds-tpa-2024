package ar.edu.utn.frba.dds.server;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;

public class Router {

    // TEMP
    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public static void init(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/home"));

        app.get("/login", new SessionController(usuarioRepository)::index);
        app.post("/login", new SessionController(usuarioRepository)::create);

        app.get("/home", ctx -> ctx.render("home/home.hbs"));

        app.get("/heladeras/new", ctx -> ctx.render("heladeras/heladera_crear.hbs"));

        app.get("/heladeras/unId", ctx -> ctx.render("heladeras/heladera_detalle.hbs"));

        app.get("/heladeras", ctx -> ctx.render("heladeras/heladeras.hbs"));

        app.get("/colaboraciones/donacion-dinero/new", ctx -> ctx.render("colaboraciones/donacion_dinero_crear.hbs"));
        app.get("/colaboraciones/donacion-vianda/new", ctx -> ctx.render("colaboraciones/donacion_vianda_crear.hbs"));
        app.get("/colaboraciones/registro-persona-vulnerable/new", ctx -> ctx.render("colaboraciones/registro_pv_crear.hbs"));
        app.get("/colaboraciones/entrega-viandas/new", ctx -> ctx.result("PENDIENTE"));
        app.get("/colaboraciones/distribucion-viandas/new", ctx -> ctx.render("colaboraciones/distribucion_viandas_crear.hbs"));
        app.get("/colaboraciones/oferta-producto-servicio/new", ctx -> ctx.render("colaboraciones/oferta_prod_serv_crear.hbs"));
        app.get("/colaboraciones/encargarse-de-heladeras/new", ctx -> ctx.render("colaboraciones/encargarse_de_heladera_crear.hbs"));

        app.get("/colaboraciones", ctx -> ctx.render("colaboraciones/colaboraciones.hbs"));

        app.get("/test", ctx -> ctx.result("DDS TPA"));
        app.get("/image/{id}", ctx -> ctx.result("IMAGEN"));

    }

    public static void initRoutes(JavalinConfig config) {
        config.router.apiBuilder(() -> {
            path("/", () -> get(ctx -> ctx.redirect("/home")));
            
            path("/login", () -> {
                get(new SessionController(usuarioRepository)::index);
                post(new SessionController(usuarioRepository)::create);
            });

            path("/home", () -> get(ctx -> ctx.render("home/home.hbs")));

            path("/heladeras", () -> {
                get(ctx -> ctx.render("heladeras/heladeras.hbs"));
                post(ctx -> ctx.result("OPERACION EXITOSA"));
                path("/new", () -> get(ctx -> ctx.render("heladeras/heladera_crear.hbs")));
                path("/{id}", () -> get(ctx -> ctx.render("heladeras/heladera_detalle.hbs")));
            });
        });

    }

}
