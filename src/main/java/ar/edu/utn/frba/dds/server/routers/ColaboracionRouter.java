package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.colaboraciones.*;
import ar.edu.utn.frba.dds.controllers.personaVulnerable.PersonaVulnerableController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class ColaboracionRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() ->
                path("/colaboraciones", () -> {
                    get(ServiceLocator.instanceOf(ColaboracionController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN);
                    post("/migrate", ServiceLocator.instanceOf(ColaboracionController.class)::cargarColaboraciones, TipoRol.ADMIN);

                    this.routeDonacionDinero();
                    this.routeDonacionVianda();
                    this.routeRegistroPersonaVulnerable();
                    this.routeDistribucionViandas();
                    this.routeOfertaProductoServicio();
                    this.routeEncargarseDeHeladeras();

                    path("/entrega-viandas", () -> {
                        // TODO - ver que hacer paraColaborador este
                    });
                }));
    }

    private void routeDonacionDinero() {
        path("/donacion-dinero", () -> {
            // TODO - get (tipo filtro por las colaboraciones general)

            post(ServiceLocator.instanceOf(DonacionDineroController.class)::save, TipoRol.COLABORADOR);

            get("/new", ServiceLocator.instanceOf(DonacionDineroController.class)::create, TipoRol.COLABORADOR);
            get("/{id}", ServiceLocator.instanceOf(DonacionDineroController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
        });
    }

    private void routeDonacionVianda() {
        path("/donacion-vianda", () -> {
            post(ServiceLocator.instanceOf(DonacionViandaController.class)::save, TipoRol.COLABORADOR);

            get("/new", ServiceLocator.instanceOf(DonacionViandaController.class)::create, TipoRol.COLABORADOR);
            get("/{id}", ctx -> ctx.result("DETALLE DONACION"), TipoRol.COLABORADOR, TipoRol.ADMIN);
        });
    }

    private void routeRegistroPersonaVulnerable() {
        path("/registro-persona-vulnerable", () -> {
            post(ServiceLocator.instanceOf(PersonaVulnerableController.class)::save, TipoRol.COLABORADOR);

            get("/new", ServiceLocator.instanceOf(PersonaVulnerableController.class)::create, TipoRol.COLABORADOR);
            get("/{id}", ctx -> ctx.result("DETALLE REGISTRO"), TipoRol.COLABORADOR, TipoRol.ADMIN);
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
            post(ServiceLocator.instanceOf(OfertaProductosServiciosController.class)::save);

            get("/new", ServiceLocator.instanceOf(OfertaProductosServiciosController.class)::create);
            get("/{id}", ctx -> ctx.result("DETALLE OFERTA"));
        });
    }

    private void routeEncargarseDeHeladeras() {
        path("/encargarse-por-heladeras", () -> {
            post(ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::save);

            get("/new", ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::create);
            get("/{id}", ctx -> ctx.result("DETALLE ENCARGO"));
        });
    }

}
