package ar.edu.utn.frba.dds.controllers.Incidente;

import static ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente.FALLA_TECNICA;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
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
import io.javalin.http.UploadedFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FallaTecnicaController extends ColaboradorPorSession {

    private final IncidenteService incidenteService;
    private final HeladeraService heladeraService;

    public FallaTecnicaController(IncidenteService incidenteService,
                                  HeladeraService heladeraService,
                                  ColaboradorService colaboradorService,
                                  UsuarioService usuarioService) {
        super(usuarioService, colaboradorService);
        this.incidenteService = incidenteService;
        this.heladeraService = heladeraService;
    }

    public void create(Context context) {
        Colaborador colaborador = obtenerColaboradorPorSession(context);
        Map<String, Object> model = new HashMap<>();
        model.put("colaborador", colaborador);

        context.render("falla_tecnica/falla_tecnica_crear.hbs", model);
    }

    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = obtenerColaboradorPorSession(context);

            Optional<Heladera> heladera = this.heladeraService.buscarHeladeraPorNombre(context.formParam("nombre"));
            if (heladera.isEmpty()) {
                context.status(404).result("Heladera no encontrada");
                return;
            }

            String descripcion = context.formParamAsClass("descripcion", String.class).get();

            UploadedFile uploadedFile = context.uploadedFile("imagen");
            if (uploadedFile == null) {
                context.status(400).result("Imagen no proporcionada");
                return;
            }
            String pathImagen = this.incidenteService.guardarArchivo(uploadedFile);

            Incidente nuevaFallaTecnica = new Incidente(
                    heladera.get(),
                    LocalDateTime.now(),
                    FALLA_TECNICA,
                    colaborador,
                    descripcion,
                    new Imagen(pathImagen)
            );

            this.incidenteService.guardarIncidente(nuevaFallaTecnica);
            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/reportar_falla", "Reportar otra Falla"));

        } catch (Exception e) {
            redirectDTOS.add(new RedirectDTO("/reportar_falla", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

}
