package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.controllers.suscripcion.SuscripcionHeladeraController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

public class HeladeraRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {
        config.apiBuilder(() ->
                path("/heladeras", () -> {
                    get(ServiceLocator.instanceOf(HeladeraController.class)::index, TipoRol.COLABORADOR, TipoRol.ADMIN);
                    post(ServiceLocator.instanceOf(HeladeraController.class)::save, TipoRol.ADMIN);

                    get("/new", ServiceLocator.instanceOf(HeladeraController.class)::create, TipoRol.ADMIN);

                    path("/{id}", () -> {
                        get(ServiceLocator.instanceOf(HeladeraController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
                        post(ServiceLocator.instanceOf(HeladeraController.class)::update, TipoRol.ADMIN, TipoRol.COLABORADOR);

                        get("/edit", ServiceLocator.instanceOf(HeladeraController.class)::edit, TipoRol.ADMIN, TipoRol.COLABORADOR);

                        path("/suscribirse", () -> {
                            get(ctx -> ctx.render("suscripcion/suscripciones.hbs"), TipoRol.COLABORADOR );

                            post("/falla-tecnica", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::saveFallaHeladera, TipoRol.COLABORADOR);
                            post("/falta-viandas", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::saveFaltaVianda, TipoRol.COLABORADOR);
                            post("/heladera-llena", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::saveHeladeraLlena, TipoRol.COLABORADOR);

                            get("/falla-tecnica", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::createFallaHeladera, TipoRol.COLABORADOR);
                            get("/falta-viandas", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::createFaltaVianda, TipoRol.COLABORADOR);
                            get("/heladera-llena", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::createHeladeraLlena, TipoRol.COLABORADOR);

                        });
                    });
                })
        );
    }
}
