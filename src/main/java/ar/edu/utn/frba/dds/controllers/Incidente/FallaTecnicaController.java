package ar.edu.utn.frba.dds.controllers.Incidente;

import static ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente.FALLA_TECNICA;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.services.Incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.Optional;

public class FallaTecnicaController {

    private final IncidenteService incidenteService;
    private final ColaboradorService colaboradorService;

    private final HeladeraService heladeraService;

    public FallaTecnicaController(IncidenteService incidenteService,
                                  ColaboradorService colaboradorService,
                                  HeladeraService heladeraService) {

        this.incidenteService = incidenteService;
        this.colaboradorService = colaboradorService;
        this.heladeraService = heladeraService;
    }

    public void create(Context context) {

        String colaboradorId = context.sessionAttribute("idUsuario");

        Optional<Colaborador> colaborador = colaboradorService.buscarPorId(colaboradorId);
        if (colaborador.isEmpty()) {
            context.status(404).result("Colaborador no encontrado");
            return;
        }

        // context.render(); TODO - redireccionar a la vista
    }

    public void save(Context context) {

        String colaboradorId = context.sessionAttribute("idUsuario");

        Optional<Colaborador> colaborador = colaboradorService.buscarPorId(colaboradorId);
        if (colaborador.isEmpty()) {
            context.status(404).result("Colaborador no encontrado");
            return;
        }

        Optional<Heladera> heladera = heladeraService.buscarHeladeraPorNombre(context.formParam("nombre"));
        if (heladera.isEmpty()) {
            context.status(404).result("Heladera no encontrada");
            return;
        }

        Incidente nuevaFallaTecnica = new Incidente(
                heladera.get(),
                LocalDateTime.now(),
                FALLA_TECNICA,
                colaborador.get(),
                String.valueOf(context.formParam("description")),
                new Imagen(String.valueOf(context.formParam("path"))));

        this.incidenteService.guardarIncidente(nuevaFallaTecnica);

        // context.redirect() TODO - redireccionar a la vista
    }
}
