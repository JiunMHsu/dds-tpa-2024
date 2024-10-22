package ar.edu.utn.frba.dds.server.routers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.Incidente.AlertaController;
import ar.edu.utn.frba.dds.controllers.Incidente.FallaTecnicaController;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.JavalinConfig;

public class IncidenteRouter implements IRouter {

    @Override
    public void apply(JavalinConfig config) {
        config.router.apiBuilder(() -> {
          path("/alertas", () -> {
            get(ServiceLocator.instanceOf(AlertaController.class)::index);

            path("/{id}", () -> {
              get(ServiceLocator.instanceOf(AlertaController.class)::show);
            });
          });

          path("/reportar_falla", () -> {
            post(ServiceLocator.instanceOf(FallaTecnicaController.class)::save, TipoRol.COLABORADOR);

            get(ServiceLocator.instanceOf(FallaTecnicaController.class)::create, TipoRol.COLABORADOR);

          });

        });
    }
}
