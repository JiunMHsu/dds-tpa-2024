package ar.edu.utn.frba.dds.server.routers;

import io.javalin.config.JavalinConfig;

public interface IRouter {
    void apply(JavalinConfig config);
}
