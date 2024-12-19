package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.incidente.FallaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFallaTecnica;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
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

public class VisitaTecnicoController extends TecnicoRequired {

  private final VisitaTecnicoService visitaTecnicoService;
  private final IncidenteService incidenteService;
  private final HeladeraService heladeraService;

  public VisitaTecnicoController(UsuarioService usuarioService,
                                 TecnicoService tecnicoService,
                                 VisitaTecnicoService visitaTecnicoService,
                                 IncidenteService incidenteService,
                                 HeladeraService heladeraService) {

    super(usuarioService, tecnicoService);
    this.visitaTecnicoService = visitaTecnicoService;
    this.incidenteService = incidenteService;
    this.heladeraService = heladeraService;
  }

  public void show(Context context) {
  }

  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();

    try {
      String fallaId = context.queryParamAsClass("fallaId", String.class)
          .getOrThrow(ValidationException::new);

      Incidente falla = this.incidenteService.buscarIncidentePorId(fallaId)
          .orElseThrow(ResourceNotFoundException::new);

      if (falla.getFallaResuelta()) {
        throw new InvalidFallaTecnica("El incidente ya está resuelto");
      }

      model.put("falla", FallaTecnicaDTO.completa(falla));
      render(context, "visitas_tecnico/visita_tecnico_crear.hbs", model);
    } catch (ValidationException | InvalidFallaTecnica e) {
      render(context, "visitas_tecnico/falla_tecnica_invalida.hbs", model);
    }
  }

  public void save(Context context) { // TODO - Ver desp que matchee para Colaborador las vistas

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    Tecnico tecnico = tecnicoFromSession(context);

    try {
      String fallaId = context.queryParamAsClass("fallaId", String.class)
          .getOrThrow(ValidationException::new);

      Incidente fallaTecnica = this.incidenteService.buscarIncidentePorId(fallaId)
          .orElseThrow(ResourceNotFoundException::new);
      LocalDateTime fechaHoraVisita = context.formParamAsClass("fecha-hora-visita", LocalDateTime.class).get();

      // va como
      Boolean resuelta = context.formParamAsClass("resuelta", Boolean.class).get();

      VisitaTecnico visitaTecnico = VisitaTecnico.por(
          tecnico,
          fallaTecnica,
          fallaTecnica.getHeladera(),
          fechaHoraVisita,
          context.formParamAsClass("descripcion", String.class).get(),
          new Imagen(context.formParamAsClass("foto", String.class).get())
      );

      this.visitaTecnicoService.registrarVisita(visitaTecnico);
    } catch (ValidationException | ResourceNotFoundException e) {
      redirectDTOS.add(new RedirectDTO("/home", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }
}
