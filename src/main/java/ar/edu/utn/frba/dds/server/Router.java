package ar.edu.utn.frba.dds.server;

import io.javalin.Javalin;

public class Router {

    public static void init(Javalin app) {
        app.get("/", ctx -> ctx.render("home.hbs"));
        app.get("/test", ctx -> ctx.result("DDS TPA"));
    }

}
