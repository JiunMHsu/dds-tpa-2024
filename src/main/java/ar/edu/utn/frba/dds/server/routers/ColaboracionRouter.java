package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DistribucionViandasController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DonacionDineroController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.HacerseCargoHeladeraController;
import ar.edu.utn.frba.dds.controllers.personaVulnerable.PersonaVulnerableController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class ColaboracionRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() ->
                path("/colaboraciones", () -> {
                    get(ctx -> ctx.render("colaboraciones/colaboraciones.hbs"));

                    this.routeDonacionDinero();
                    this.routeDonacionVianda();
                    this.routeRegistroPersonaVulnerable();
                    this.routeDistribucionViandas();
                    this.routeOfertaProductoServicio();
                    this.routeEncargarseDeHeladeras();

                    path("/entrega-viandas", () -> {
                        // TODO - ver que hacer con este
                    });
                }));
    }

    private void routeDonacionDinero() {
        path("/donacion-dinero", () -> {
            // TODO - get (tipo filtro de las colaboraciones general)

            post(ServiceLocator.instanceOf(DonacionDineroController.class)::save, TipoRol.COLABORADOR);

            get("/new", ServiceLocator.instanceOf(DonacionDineroController.class)::create, TipoRol.COLABORADOR);
            get("/{id}", ServiceLocator.instanceOf(DonacionDineroController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
        });
    }

    private void routeDonacionVianda() {
        path("/donacion-vianda", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/donacion_vianda_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE DONACION"));
        });
    }

    private void routeRegistroPersonaVulnerable() {
        path("/registro-persona-vulnerable", () -> {
            post(ServiceLocator.instanceOf(PersonaVulnerableController.class)::save);

            get("/new", ServiceLocator.instanceOf(PersonaVulnerableController.class)::create);
            get("/{id}", ctx -> ctx.result("DETALLE REGISTRO"));
        });
    }

    private void routeDistribucionViandas() {
        path("/distribucion-viandas", () -> {
            post(ServiceLocator.instanceOf(DistribucionViandasController.class)::save);

            get("/new", ServiceLocator.instanceOf(DistribucionViandasController.class)::create);
            get("/{id}", ctx -> ctx.result("DETALLE DISTRIBUCION"));
        });
    }

    private void routeOfertaProductoServicio() {
        path("/oferta-producto-servicio", () -> {
            post(ctx -> ctx.result("FORMULARIO ENVIADO"));

            get("/new", ctx -> ctx.render("colaboraciones/oferta_prod_serv_crear.hbs"));
            get("/{id}", ctx -> ctx.result("DETALLE OFERTA"));
        });
    }

    private void routeEncargarseDeHeladeras() {
        path("/encargarse-de-heladeras", () -> {
            post(ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::save);

            get("/new", ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::create);
            get("/{id}", ctx -> ctx.result("DETALLE ENCARGO"));
        });
    }

}
