package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import io.javalin.config.JavalinConfig;

public class ColaboracionRouter {

    public static void apply(JavalinConfig config) {
        config.router.apiBuilder(() ->
                path("/colaboraciones", () -> {
                    get(ctx -> ctx.render("colaboraciones/colaboraciones.hbs"));

                    routeDonacionDinero();
                    routeDonacionVianda();
                    routeRegistroPersonaVulnerable();
                    routeDistribucionViandas();
                    routeOfertaProductoServicio();
                    routeEncargarseDeHeladeras();

                    path("/entrega-viandas", () -> {
                        // TODO - ver que hacer con este
                    });
                }));
    }

    private static void routeDonacionDinero() {
        path("/donacion-dinero", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/donacion_dinero_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE DONACION"));
        });
    }

    private static void routeDonacionVianda() {
        path("/donacion-vianda", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/donacion_vianda_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE DONACION"));
        });
    }

    private static void routeRegistroPersonaVulnerable() {
        path("/registro-persona-vulnerable", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/registro_pv_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE REGISTRO"));
        });
    }

    private static void routeDistribucionViandas() {
        path("/distribucion-viandas", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/distribucion_viandas_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE DISTRIBUCION"));
        });
    }

    private static void routeOfertaProductoServicio() {
        path("/oferta-producto-servicio", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/oferta_prod_serv_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE OFERTA"));
        });
    }

    private static void routeEncargarseDeHeladeras() {
        path("/encargarse-de-heladeras", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/encargarse_de_heladera_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE ENCARGO"));
        });
    }
}
