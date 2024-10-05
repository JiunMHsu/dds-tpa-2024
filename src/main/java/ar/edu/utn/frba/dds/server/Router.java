package ar.edu.utn.frba.dds.server;

import io.javalin.Javalin;

public class Router {

    public static void init(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/home"));

        app.get("/login", ctx -> ctx.render("login/login.hbs"));
        app.post("/login", ctx -> ctx.render("login/login_retry.hbs"));

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
    }

}
