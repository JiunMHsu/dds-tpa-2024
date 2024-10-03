package ar.edu.utn.frba.dds.server;

import io.javalin.Javalin;

public class Router {

    public static void init(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/home"));

        app.get("/home", ctx -> ctx.render("home/home.hbs"));

        app.get("/heladeras/nuevo", ctx -> ctx.render("heladeras/heladera_crear.hbs"));

        app.get("/heladeras/unId", ctx -> ctx.render("heladeras/heladera_detalle.hbs"));

        app.get("/heladeras", ctx -> ctx.render("heladeras/heladeras.hbs"));

        app.get("/colaboraciones/donacion-dinero/nuevo", ctx -> ctx.render("colaboraciones/donacion_dinero_crear.hbs"));
        app.get("/colaboraciones/donacion-vianda/nuevo", ctx -> ctx.result("PENDIENTE"));
        app.get("/colaboraciones/registro-persona-vulnerable/nuevo", ctx -> ctx.result("PENDIENTE"));
        app.get("/colaboraciones/entrega-viandas/nuevo", ctx -> ctx.result("PENDIENTE"));
        app.get("/colaboraciones/distribucion-viandas/nuevo", ctx -> ctx.render("colaboraciones/distribucion_viandas_crear.hbs"));
        app.get("/colaboraciones/oferta-producto-servicio/nuevo", ctx -> ctx.result("PENDIENTE"));
        app.get("/colaboraciones/tomar-cargo-heladeras/nuevo", ctx -> ctx.result("PENDIENTE"));

        app.get("/colaboraciones", ctx -> ctx.render("colaboraciones/colaboraciones.hbs"));

        app.get("/test", ctx -> ctx.result("DDS TPA"));
    }

}
