package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.middlewares.AuthMiddleware;
import ar.edu.utn.frba.dds.server.handlers.AppHandlers;
import ar.edu.utn.frba.dds.server.routers.Routers;
import ar.edu.utn.frba.dds.utils.AppProperties;
import ar.edu.utn.frba.dds.utils.Initializer;
import ar.edu.utn.frba.dds.utils.JavalinRenderer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import java.io.IOException;

/**
 * Clase que inicializa el servidor Javalin y configura los middlewares y rutas.
 */
public class Server {

  private static Javalin app = null;

  /**
   * Devuelve la instancia de la aplicación Javalin.
   *
   * @return Instancia de la aplicación Javalin.
   */
  public static Javalin app() {
    if (app == null) {
      throw new RuntimeException("App no inicializada");
    }

    return app;
  }

  /**
   * Inicializa el servidor Javalin y configura los middlewares y rutas.
   */
  public static void init() {
    if (app != null) {
      return;
    }

    int port = AppProperties.getInstance().intPropertyFromName("SERVER_PORT");
    app = Javalin.create(Server::applyConfig)
        .before(ctx -> {
          ctx.res().setHeader("Cache-Control",
              "no-store, no-cache, must-revalidate, proxy-revalidate");
          ctx.res().setHeader("Pragma", "no-cache");
          ctx.res().setHeader("Expires", "0");
        })
        .error(HttpStatus.NOT_FOUND, context -> {
          throw new ResourceNotFoundException("Resource not found for endpoint " + context.path());
        })
        .start(port);

    AppHandlers.apply(app);

    if (AppProperties.getInstance().boolPropertyFromName("DEV_MODE")) {
      Initializer.init();
    }
  }

  private static void applyConfig(JavalinConfig config) {
    setStaticFiles(config);
    setFileRenderer(config);
    AuthMiddleware.apply(config.router);
    Routers.apply(config.router);
  }

  private static void setStaticFiles(JavalinConfig config) {
    config.staticFiles.add(staticFiles -> {
      staticFiles.hostedPath = "/";
      staticFiles.directory = "/public";
    });
  }

  private static void setFileRenderer(JavalinConfig config) {
    config.fileRenderer(new JavalinRenderer()
        .register("hbs", (path, model, context) -> {
          Handlebars handlebars = new Handlebars();
          Template template;

          try {
            template = handlebars.compile(
                "templates/" + path.replace(".hbs", "")
            );
            return template.apply(model);
          } catch (IOException e) {
            e.printStackTrace();
            context.status(HttpStatus.NOT_FOUND);
            return "No se encuentra la página indicada...";
          }
        })
    );
  }
}
