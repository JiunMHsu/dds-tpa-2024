package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicoService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.*;

public class VisitaTecnicoController {

    private final VisitaTecnicoService visitaTecnicoService;
    private final TecnicoService tecnicoService;
    private final HeladeraService heladeraService;

    public VisitaTecnicoController (VisitaTecnicoService visitaTecnicoService,
                                    TecnicoService tecnicoService,
                                    HeladeraService heladeraService) {

        this.visitaTecnicoService = visitaTecnicoService;
        this.tecnicoService = tecnicoService;
        this.heladeraService = heladeraService;
    }

    public void create(Context context) {
        // TODO - solo context.render?
    }

    public void save(Context context) { // TODO - Ver desp que matchee con las vistas

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        Tecnico tecnico = new Tecnico(); // TODO - VER --> posible refactor Tecnico

        try {

            String heladeraId = context.formParamAsClass("id-heladera", String.class).get();
            Optional<Heladera> heladera = this.heladeraService.buscarPorId(heladeraId);

            if (heladera.isEmpty()) {
                throw new ResourceNotFoundException("No se encontró heladera con id " + heladeraId);
            }

            Boolean resuelta = context.formParamAsClass("resuelta", Boolean.class).get();

            // Creo q va ser mejor delegar la instanciacion al service

            VisitaTecnico visitaTecnico = VisitaTecnico.por(
                    tecnico,
                    heladera.get(),
                    LocalDateTime.now(),
                    context.formParamAsClass("descripcion", String.class).get(),
                    new Imagen(context.formParamAsClass("foto", String.class).get()),
                    resuelta
            );

            this.visitaTecnicoService.registrarVisita();

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/home", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }
}
