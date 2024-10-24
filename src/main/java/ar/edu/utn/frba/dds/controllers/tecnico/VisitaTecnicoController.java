package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.*;

public class VisitaTecnicoController {

    private final VisitaTecnicoService visitaTecnicoService;
    private final TecnicoService tecnicoService;
    private final HeladeraService heladeraService;
    private final UsuarioService usuarioService;


    public VisitaTecnicoController (VisitaTecnicoService visitaTecnicoService,
                                    TecnicoService tecnicoService,
                                    HeladeraService heladeraService,
                                    UsuarioService usuarioService) {

        this.visitaTecnicoService = visitaTecnicoService;
        this.tecnicoService = tecnicoService;
        this.heladeraService = heladeraService;
        this.usuarioService = usuarioService;
    }

    public void create(Context context) {
        // TODO - solo context.render?
    }

    public void save(Context context) { // TODO - Ver desp que matchee con las vistas

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        String userId = context.sessionAttribute("userId");

        Optional<Usuario> usuarioSession = usuarioService.obtenerUsuarioPorID(userId);
        if (usuarioSession.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el usuario con id " + userId);
        }

        Usuario usuario = usuarioSession.get();

        Optional<Tecnico> tecnicoSession = tecnicoService.obtenerTecnicoPorUsuario(usuarioSession.get());
        if (tecnicoSession.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el tecnico con usuario " + usuario.getNombre());
        }

        Tecnico tecnico = tecnicoSession.get();

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
