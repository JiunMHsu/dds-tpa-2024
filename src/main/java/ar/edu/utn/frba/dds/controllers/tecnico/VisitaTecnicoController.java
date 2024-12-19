package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.incidente.FallaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFallaTecnica;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.images.ImageService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitaTecnicoController extends TecnicoRequired {

  private final VisitaTecnicoService visitaTecnicoService;
  private final IncidenteService incidenteService;
  private final ImageService fileService;

  public VisitaTecnicoController(UsuarioService usuarioService,
                                 TecnicoService tecnicoService,
                                 VisitaTecnicoService visitaTecnicoService,
                                 IncidenteService incidenteService,
                                 ImageService fileService) {

    super(usuarioService, tecnicoService);
    this.visitaTecnicoService = visitaTecnicoService;
    this.incidenteService = incidenteService;
    this.fileService = fileService;
  }

  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();

    try {
      String fallaId = context.queryParamAsClass("fallaId", String.class)
          .getOrThrow(ValidationException::new);

      Incidente falla = this.incidenteService.buscarIncidentePorId(fallaId)
          .orElseThrow(ResourceNotFoundException::new);

      if (falla.getResuelta()) {
        throw new InvalidFallaTecnica("El incidente ya está resuelto");
      }

      model.put("falla", FallaTecnicaDTO.completa(falla));
      render(context, "visitas_tecnico/visita_tecnico_crear.hbs", model);
    } catch (ValidationException | InvalidFallaTecnica e) {
      render(context, "visitas_tecnico/falla_tecnica_invalida.hbs", model);
    }
  }

  public void show(Context context) {
  }

  public void save(Context context) {

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    Tecnico tecnico = tecnicoFromSession(context);

    try {
      String fallaId = context.queryParamAsClass("fallaId", String.class)
          .getOrThrow(ValidationException::new);

      Incidente fallaTecnica = this.incidenteService.buscarIncidentePorId(fallaId)
          .orElseThrow(ResourceNotFoundException::new);
      LocalDateTime fechaHoraVisita = DateTimeParser.fromFormInput(
          context.formParamAsClass("fecha-hora-visita", String.class).get()
      );
      String descripcion = context.formParamAsClass("descripcion", String.class).get();

      Boolean resuelta = switch (context.formParamAsClass("estado-falla", String.class).getOrDefault("pendiente")) {
        case "resuelta" -> true;
        case "pendiente" -> false;
        default -> throw new InvalidFormParamException("estado falla invalido");
      };

      UploadedFile uploadedFile = context.uploadedFile("foto");
      if (uploadedFile == null) throw new InvalidFormParamException();
      String pathImagen = fileService.guardarImagen(uploadedFile.content(), uploadedFile.extension());

      System.out.println(fechaHoraVisita);
      System.out.println(resuelta);


      VisitaTecnico visitaTecnico = VisitaTecnico.por(
          tecnico,
          fallaTecnica,
          fallaTecnica.getHeladera(),
          fechaHoraVisita,
          descripcion,
          new Imagen(pathImagen)
      );

      this.visitaTecnicoService.registrarVisita(visitaTecnico);
      if (resuelta) this.incidenteService.resolverIncidente(fallaTecnica);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/fallas-tecnicas", "Registrar otra Visita"));

    } catch (ValidationException
             | ResourceNotFoundException
             | InvalidFormParamException
             | IOException e) {
      redirectDTOS.add(new RedirectDTO("/fallas-tecnicas", "Ver Fallas Tecnicas"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }
}
