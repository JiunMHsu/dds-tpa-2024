package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.TecnicoDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.IncicenteToFixException;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.images.ImageService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicaService;
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

public class VisitaTecnicaController extends TecnicoRequired {

  private final VisitaTecnicaService visitaTecnicaService;
  private final IncidenteService incidenteService;
  private final ImageService fileService;

  public VisitaTecnicaController(UsuarioService usuarioService,
                                 TecnicoService tecnicoService,
                                 VisitaTecnicaService visitaTecnicaService,
                                 IncidenteService incidenteService,
                                 ImageService fileService) {

    super(usuarioService, tecnicoService);
    this.visitaTecnicaService = visitaTecnicaService;
    this.incidenteService = incidenteService;
    this.fileService = fileService;
  }

  public void index(Context context) {
    List<VisitaTecnica> visitasTecnicas = this.visitaTecnicaService.buscarTodas();
    List<VisitaTecnicaDTO> visitaTecnicaDTO = visitasTecnicas.stream()
        .map(VisitaTecnicaDTO::preview)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("visitas-tecnicas", visitaTecnicaDTO);
    render(context, "visitas_tecnicas/visitas_tecnicas.hbs", model);
  }

  public void show(Context context) {
    // TODO - implementar
  }

  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();

    try {
      String incidenteId = context.queryParamAsClass("incidente", String.class)
          .getOrThrow(ValidationException::new);

      Incidente incidente = this.incidenteService.buscarIncidentePorId(incidenteId)
          .orElseThrow(ResourceNotFoundException::new);

      if (incidente.getResuelta()) {
        throw new IncicenteToFixException("El incidente ya está resuelto");
      }

      model.put("incidente", IncidenteDTO.preview(incidente));
      render(context, "visitas_tecnicas/visita_tecnica_crear.hbs", model);
    } catch (ValidationException | IncicenteToFixException e) {
      List<RedirectDTO> redirectDTOS = new ArrayList<>();
      boolean operationSuccess = false;
      redirectDTOS.add(new RedirectDTO("/incidentes", "Ver Incidentes"));
      model.put("fail", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }

  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    Tecnico tecnico = tecnicoFromSession(context);

    try {
      String incidenteId = context.queryParamAsClass("incidente", String.class)
          .getOrThrow(ValidationException::new);

      Incidente incidente = this.incidenteService.buscarIncidentePorId(incidenteId)
          .orElseThrow(ResourceNotFoundException::new);

      if (incidente.getResuelta()) {
        throw new IncicenteToFixException("El incidente ya está resuelto");
      }

      LocalDateTime fechaHoraVisita = DateTimeParser.fromFormInput(
          context.formParamAsClass("fecha-hora-visita", String.class).get());

      String descripcion = context.formParamAsClass("descripcion", String.class).get();

      Boolean resuelta = switch (context.formParamAsClass("estado-incidente", String.class).getOrDefault("pendiente")) {
        case "resuelta" -> true;
        case "pendiente" -> false;
        default -> throw new InvalidFormParamException("estado incidente invalido");
      };

      UploadedFile uploadedFile = context.uploadedFile("foto");
      if (uploadedFile == null) throw new InvalidFormParamException();
      String pathImagen = fileService.guardarImagen(uploadedFile.content(), uploadedFile.extension());

      VisitaTecnica visitaTecnica = VisitaTecnica.por(
          tecnico,
          incidente,
          incidente.getHeladera(),
          fechaHoraVisita,
          descripcion,
          resuelta,
          new Imagen(pathImagen)
      );

      this.visitaTecnicaService.registrarVisita(visitaTecnica);
      if (resuelta) this.incidenteService.resolverIncidente(incidente);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/incidentes", "Registrar otra Visita"));

    } catch (ValidationException
             | IncicenteToFixException
             | InvalidFormParamException
             | IOException e) {
      redirectDTOS.add(new RedirectDTO("/incidentes", "Ver Incidentes"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }
}
