package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.CreateVisitaTecnicaDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.IncicenteToFixException;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.NonTecnicoException;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.permissions.TecnicoRequired;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de visitas técnicas.
 */
public class VisitaTecnicaController extends TecnicoRequired {

  private final VisitaTecnicaService visitaTecnicaService;
  private final IncidenteService incidenteService;

  /**
   * Constructor de VisitaTecnicaController.
   *
   * @param usuarioService       Servicio de Usuario
   * @param tecnicoService       Servicio de Tecnico
   * @param visitaTecnicaService Servicio de VisitaTecnica
   * @param incidenteService     Servicio de Incidente
   */
  public VisitaTecnicaController(UsuarioService usuarioService,
                                 TecnicoService tecnicoService,
                                 VisitaTecnicaService visitaTecnicaService,
                                 IncidenteService incidenteService) {
    super(usuarioService, tecnicoService);
    this.visitaTecnicaService = visitaTecnicaService;
    this.incidenteService = incidenteService;
  }

  /**
   * Muestra la lista de visitas técnicas.
   *
   * @param context Context de Javalin
   */
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<VisitaTecnicaDTO> visitasTecnicas = new ArrayList<>();

    try {
      Tecnico tecnico = tecnicoFromSession(context);
      visitasTecnicas = this.visitaTecnicaService.buscarPorTecnico(tecnico);
    } catch (NonTecnicoException e) {
      visitasTecnicas = this.visitaTecnicaService.buscarTodas();
    } finally {
      model.put("visitas-tecnicas", visitasTecnicas);
      render(context, "visitas_tecnicas/visitas_tecnicas.hbs", model);
    }
  }

  /**
   * Muestra una visita técnica.
   *
   * @param context Context de Javalin
   */
  public void create(Context context) {
    try {
      String incidenteId = context.queryParamAsClass("incidente", String.class)
          .getOrThrow(ValidationException::new);

      Incidente incidente = this.incidenteService.buscarIncidentePorId(incidenteId);

      if (incidente.getEsResuelta()) {
        throw new IncicenteToFixException("El incidente ya está resuelto");
      }

      Map<String, Object> model = new HashMap<>();
      model.put("incidente", IncidenteDTO.fromIncidente(incidente));
      render(context, "visitas_tecnicas/visita_tecnica_crear.hbs", model);
    } catch (ValidationException | IncicenteToFixException e) {
      render(context, "visitas_tecnicas/incidente_invalido.hbs");
    }
  }

  /**
   * Guarda una visita técnica.
   *
   * @param context Context de Javalin
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    Tecnico tecnico = tecnicoFromSession(context);

    try {

      String estado = context.formParamAsClass("estado-incidente", String.class)
          .getOrDefault("pendiente");

      boolean pudoResolverse = switch (estado) {
        case "resuelta" -> true;
        case "pendiente" -> false;
        default -> throw new InvalidFormParamException("estado incidente invalido");
      };

      CreateVisitaTecnicaDTO nuevaVisita = new CreateVisitaTecnicaDTO(
          context.queryParamAsClass("incidente", String.class)
              .getOrThrow(ValidationException::new),
          DateTimeParser.fromFormInput(
              context.formParamAsClass("fecha-hora-visita", String.class).get()),
          context.formParamAsClass("descripcion", String.class).get(),
          context.uploadedFile("foto"),
          pudoResolverse
      );

      this.visitaTecnicaService.registrarVisita(tecnico, nuevaVisita);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/incidentes", "Registrar otra Visita"));

    } catch (ValidationException
             | IncicenteToFixException
             | InvalidFormParamException
             | IOException e) {
      redirects.add(new RedirectDTO("/incidentes", "Ver Incidentes"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }
}
