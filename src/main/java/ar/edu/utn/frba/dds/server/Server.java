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
import java.util.function.Consumer;

public class Server {

    private static Javalin app = null;

    public static Javalin app() {
        if (app == null)
            throw new RuntimeException("App no inicializada");

        return app;
    }

    public static void init() {
        if (app != null) {
            return;
        }

        int port = AppProperties.getInstance().intPropertyFromName("SERVER_PORT");
        app = Javalin.create(config())
                .error(HttpStatus.NOT_FOUND, context -> {
                    throw new ResourceNotFoundException("Resource not found");
                })
                .start(port);

        AuthMiddleware.apply(app);
        AppHandlers.apply(app);
        // Router.init(app);

        if (AppProperties.getInstance().boolPropertyFromName("DEV_MODE")) {
            Initializer.init();
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });

            setFileRenderer(config);
            Routers.apply(config);
        };
    }

    private static void setFileRenderer(JavalinConfig config) {
        config.fileRenderer(new JavalinRenderer().register("hbs", (path, model, context) -> {
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
        }));
    }
}
