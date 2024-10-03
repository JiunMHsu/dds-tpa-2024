package ar.edu.utn.frba.dds.server;

import io.javalin.Javalin;

public class Router {

    public static void init(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/home"));

        app.get("/home", ctx -> ctx.render("home/home.hbs"));

        app.get("/heladeras/nueva", ctx -> ctx.render("heladeras/heladera_crear.hbs"));

        app.get("/heladeras/unId", ctx -> ctx.render("heladeras/heladera_detalle.hbs"));

        app.get("/heladeras", ctx -> ctx.render("heladeras/heladeras.hbs"));

        app.get("/colaboraciones", ctx -> ctx.render("colaboraciones/colaboraciones.hbs"));

        app.get("/test", ctx -> ctx.result("DDS TPA"));
    }

}
