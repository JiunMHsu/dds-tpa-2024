package ar.edu.utn.frba.dds.server.handlers;

import io.javalin.Javalin;
import java.util.Arrays;

/**
 * App Handlers.
 */
public class AppHandlers {

  private final IHandler[] handlers = new IHandler[]{
      new UnauthorizedHandler(),
      new UnauthenticatedHandler(),
      new ResourceNotFoundExceptionHandler()
  };

  public static void apply(Javalin app) {
    Arrays.stream(new AppHandlers().handlers).toList().forEach(handler -> handler.setHandler(app));
  }
}
