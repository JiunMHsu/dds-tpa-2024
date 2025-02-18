package ar.edu.utn.frba.dds.server.routers;

import io.javalin.config.RouterConfig;

/**
 * Interface para los routers.
 */
//TODO check mayuscula
public interface IRouter {
  void apply(RouterConfig config);
}
