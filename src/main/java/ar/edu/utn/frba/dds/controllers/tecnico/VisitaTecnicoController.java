package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VisitaTecnicoController extends TecnicoRequired {

    private final VisitaTecnicoService visitaTecnicoService;
    private final HeladeraService heladeraService;

    public VisitaTecnicoController(UsuarioService usuarioService,
                                   TecnicoService tecnicoService,
                                   VisitaTecnicoService visitaTecnicoService,
                                   HeladeraService heladeraService) {

        super(usuarioService, tecnicoService);
        this.visitaTecnicoService = visitaTecnicoService;
        this.heladeraService = heladeraService;
    }

    public void show(Context context) {
    }

    public void create(Context context) {
        context.result("Visita Tecnico");
    }

    public void save(Context context) { // TODO - Ver desp que matchee para Colaborador las vistas

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        Tecnico tecnico = tecnicoFromSession(context);

        try {
            // id incidente por query param

            String heladeraId = context.formParamAsClass("id-heladera", String.class).get();
            Optional<Heladera> heladera = this.heladeraService.buscarPorId(heladeraId);

            if (heladera.isEmpty()) {
                throw new ResourceNotFoundException("No se encontró heladera paraColaborador id " + heladeraId);
            }

            Boolean resuelta = context.formParamAsClass("resuelta", Boolean.class).get();

            VisitaTecnico visitaTecnico = VisitaTecnico.por(
                    tecnico,
                    new Incidente(), //TODO - va el incidente recuperado por id
                    heladera.get(),
                    LocalDateTime.now(),
                    context.formParamAsClass("descripcion", String.class).get(),
                    new Imagen(context.formParamAsClass("foto", String.class).get())
            );

            this.visitaTecnicoService.registrarVisita(visitaTecnico);
        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/home", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }
}
