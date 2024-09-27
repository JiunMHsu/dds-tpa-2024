package ar.edu.utn.frba.dds.server;

import io.javalin.Javalin;

public class Router {

    public static void init(Javalin app) {
        app.get("/test", ctx -> ctx.result("DDS TPA"));

        app.get("/home", ctx -> ctx.render("home.hbs"));
    }

}
