package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.utils.AppProperties;
import io.javalin.config.RouterConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

public class Routers {

    private static final IRouter[] routers = new IRouter[]{
            new ColaboracionRouter(),
            new HeladeraRouter(),
            new PuntoIdealRouter(),
            new SuscripcionRouter(),
            new ColaboradorRouter(),
            new IncidenteRouter(),
            new CanjeDePuntosRouter(),
            new ReporteRouter(),
            new TecnicoRouter(),
            new VisitaTecnicoRouter(),
    };

    public static void apply(RouterConfig config) {
        config.apiBuilder(() -> {
            get("/", ctx -> ctx.redirect("/home"), TipoRol.COLABORADOR, TipoRol.ADMIN);
            get("/home", ctx -> ctx.render("home/home.hbs"), TipoRol.COLABORADOR, TipoRol.ADMIN);
            get("/test", ctx -> ctx.result("DDS TPA"));

            path("/login", () -> {
                get(ServiceLocator.instanceOf(SessionController.class)::index, TipoRol.GUEST);
                post(ServiceLocator.instanceOf(SessionController.class)::create, TipoRol.GUEST);
            });

            get("/image/{id}", ctx -> {
                String relativePath = AppProperties.getInstance().propertyFromName("IMAGE_DIR");
                if (relativePath == null)
                    throw new RuntimeException("No image directory found");

                String absolutePath = Path.of(relativePath).toAbsolutePath().toString();
                System.out.println(absolutePath);

                try {
                    InputStream input = new FileInputStream(absolutePath + File.separator + ctx.pathParam("id"));
                    ctx.result(input);
                } catch (FileNotFoundException e) {
                    throw new ResourceNotFoundException();
                }
            });
        });

        Arrays.stream(routers).forEach(router -> router.apply(config));
    }

}
