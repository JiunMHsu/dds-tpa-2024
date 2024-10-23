package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.files.FileService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FallaTecnicaController extends ColaboradorPorSession {

    private final IncidenteService incidenteService;
    private final HeladeraService heladeraService;
    private final FileService fileService;

    public FallaTecnicaController(IncidenteService incidenteService,
                                  HeladeraService heladeraService,
                                  FileService fileService,
                                  ColaboradorService colaboradorService,
                                  UsuarioService usuarioService) {
        super(usuarioService, colaboradorService);
        this.incidenteService = incidenteService;
        this.heladeraService = heladeraService;
        this.fileService = fileService;
    }

    public void index(Context context) {

    }

    public void show(Context context) {

    }

    public void create(Context context) {
        context.render("falla_tecnica/falla_tecnica_crear.hbs");
    }

    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = obtenerColaboradorPorSession(context);

            String nombreHeladera = context.formParamAsClass("nombre", String.class).get();
            Heladera heladera = this.heladeraService
                    .buscarPorNombre(nombreHeladera)
                    .orElseThrow(InvalidFormParamException::new);

            String descripcion = context.formParamAsClass("descripcion", String.class).get();

            UploadedFile uploadedFile = context.uploadedFile("imagen");
            if (uploadedFile == null) throw new InvalidFormParamException();
            String pathImagen = fileService.guardarImagen(uploadedFile.content(), uploadedFile.extension());

            Incidente nuevaFallaTecnica = Incidente.fallaTecnica(
                    heladera,
                    LocalDateTime.now(),
                    colaborador,
                    descripcion,
                    new Imagen(pathImagen)
            );

            this.incidenteService.guardarIncidente(nuevaFallaTecnica);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/fallas_tecnicas/new", "Reportar otra Falla"));

        } catch (ValidationException | InvalidFormParamException | IOException e) {
            redirectDTOS.add(new RedirectDTO("/fallas_tecnicas/new", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);

            context.render("post_result.hbs", model);
        }
    }

}
