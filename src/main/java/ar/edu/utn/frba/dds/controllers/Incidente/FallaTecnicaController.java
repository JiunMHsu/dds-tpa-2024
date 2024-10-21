package ar.edu.utn.frba.dds.controllers.Incidente;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.services.Incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import io.javalin.http.Context;
import static ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class FallaTecnicaController extends ColaboradorPorSession {

    private final IncidenteService incidenteService;
    private final HeladeraService heladeraService;

    public FallaTecnicaController (IncidenteService incidenteService,
                                   ColaboradorService colaboradorService,
                                   UsuarioService usuarioService,
                                   HeladeraService heladeraService) {

        super(usuarioService, colaboradorService);
        this.incidenteService = incidenteService;
        this.heladeraService = heladeraService;
    }

    public void create(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        // context.render(); TODO - redireccionar a la vista
    }

    public void save(Context context) {

        Colaborador colaborador = obtenerColaboradorPorSession(context);

        Optional<Heladera> heladera = heladeraService.buscarHeladeraPorNombre(context.formParam("nombre"));
        if (heladera.isEmpty()) {
            context.status(404).result("Heladera no encontrada");
            return;
        }

        Incidente nuevaFallaTecnica = new Incidente(
                heladera.get(),
                LocalDateTime.now(),
                FALLA_TECNICA,
                colaborador,
                String.valueOf(context.formParam("description")),
                new Imagen(String.valueOf(context.formParam("path"))));

        this.incidenteService.guardarIncidente(nuevaFallaTecnica);

        // context.redirect() TODO - redireccionar a la vista
    }
}
