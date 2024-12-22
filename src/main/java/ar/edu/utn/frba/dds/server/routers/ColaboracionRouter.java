package ar.edu.utn.frba.dds.server.routers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.colaboraciones.*;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

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
      get("/{id}", ServiceLocator.instanceOf(DonacionViandaController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
    });
  }

  private void routeRegistroPersonaVulnerable() {
    path("/registro-persona-vulnerable", () -> {
      post(ServiceLocator.instanceOf(RepartoDeTarjetaController.class)::save, TipoRol.COLABORADOR);

      get("/new", ServiceLocator.instanceOf(RepartoDeTarjetaController.class)::create, TipoRol.COLABORADOR);
      get("/{id}", ServiceLocator.instanceOf(RepartoDeTarjetaController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
    });
  }

  private void routeDistribucionViandas() {
    path("/distribucion-viandas", () -> {
      post(ServiceLocator.instanceOf(DistribucionViandasController.class)::save, TipoRol.COLABORADOR);

      get("/new", ServiceLocator.instanceOf(DistribucionViandasController.class)::create, TipoRol.COLABORADOR);
      get("/{id}", ServiceLocator.instanceOf(DistribucionViandasController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
    });
  }

  private void routeOfertaProductoServicio() {
    path("/oferta-producto-servicio", () -> {
      post(ServiceLocator.instanceOf(OfertaProductosServiciosController.class)::save, TipoRol.COLABORADOR);

      get("/new", ServiceLocator.instanceOf(OfertaProductosServiciosController.class)::create, TipoRol.COLABORADOR);
      get("/{id}", ServiceLocator.instanceOf(OfertaProductosServiciosController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
    });
  }

  private void routeEncargarseDeHeladeras() {
    path("/encargarse-de-heladeras", () -> {
      post(ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::save, TipoRol.COLABORADOR);

      get("/new", ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::create, TipoRol.COLABORADOR);
      get("/{id}", ServiceLocator.instanceOf(HacerseCargoHeladeraController.class)::show, TipoRol.COLABORADOR, TipoRol.ADMIN);
    });
  }

}
