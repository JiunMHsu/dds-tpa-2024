package ar.edu.utn.frba.dds.server.routers;

import io.javalin.config.RouterConfig;

public interface IRouter {
  void apply(RouterConfig config);
}
