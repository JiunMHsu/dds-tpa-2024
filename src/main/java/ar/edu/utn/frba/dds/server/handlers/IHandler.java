package ar.edu.utn.frba.dds.server.handlers;

import io.javalin.Javalin;

/**
 * Handler Interface.
 */
public interface IHandler {
  void setHandler(Javalin app);
}
