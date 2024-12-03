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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VisitaTecnicoController {

    private final VisitaTecnicoService visitaTecnicoService;
    private final TecnicoService tecnicoService;
    private final HeladeraService heladeraService;
    private final UsuarioService usuarioService;


    public VisitaTecnicoController(VisitaTecnicoService visitaTecnicoService,
                                   TecnicoService tecnicoService,
                                   HeladeraService heladeraService,
                                   UsuarioService usuarioService) {

        this.visitaTecnicoService = visitaTecnicoService;
        this.tecnicoService = tecnicoService;
        this.heladeraService = heladeraService;
        this.usuarioService = usuarioService;
    }

    public void index(Context context) {
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

        String userId = context.sessionAttribute("userId");

        Optional<Usuario> usuarioSession = usuarioService.obtenerUsuarioPorID(userId);
        if (usuarioSession.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el usuario paraColaborador id " + userId);
        }

        Usuario usuario = usuarioSession.get();

        Optional<Tecnico> tecnicoSession = tecnicoService.obtenerTecnicoPorUsuario(usuarioSession.get());
        if (tecnicoSession.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el tecnico paraColaborador usuario " + usuario.getNombre());
        }

        Tecnico tecnico = tecnicoSession.get();

        try {

            String heladeraId = context.formParamAsClass("id-heladera", String.class).get();
            Optional<Heladera> heladera = this.heladeraService.buscarPorId(heladeraId);

            if (heladera.isEmpty()) {
                throw new ResourceNotFoundException("No se encontró heladera paraColaborador id " + heladeraId);
            }

            Boolean resuelta = context.formParamAsClass("resuelta", Boolean.class).get();

            VisitaTecnico visitaTecnico = VisitaTecnico.por(
                    tecnico,
                    heladera.get(),
                    LocalDateTime.now(),
                    context.formParamAsClass("descripcion", String.class).get(),
                    new Imagen(context.formParamAsClass("foto", String.class).get()),
                    resuelta
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
