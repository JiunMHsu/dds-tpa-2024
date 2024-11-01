package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.suscripcion.SuscripcionHeladeraController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.*;

public class SuscripcionRouter implements IRouter {

    @Override
    public void apply(RouterConfig config) {

        config.apiBuilder(() ->

        path("/suscripciones", () -> {
            get("/new", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::create, TipoRol.COLABORADOR );

            path("/falla-tecnica", () -> {
                post(ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::saveFallaHeladera, TipoRol.COLABORADOR);

                get("/new", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::createFallaHeladera, TipoRol.COLABORADOR);
            });

            path("/falta-viandas", () -> {
                post(ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::saveFaltaVianda, TipoRol.COLABORADOR);

                get("/new", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::createFaltaVianda, TipoRol.COLABORADOR);
            });

            path("/heladera-llena", () -> {
                post(ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::saveHeladeraLlena, TipoRol.COLABORADOR);

                get("/new", ServiceLocator.instanceOf(SuscripcionHeladeraController.class)::createHeladeraLlena, TipoRol.COLABORADOR);
            });
        })
        );
    }
}
