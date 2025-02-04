package ar.edu.utn.frba.dds.controllers.incidente;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.incidente.CreateFallaTecnicaDTO;
import ar.edu.utn.frba.dds.dtos.incidente.FallaTecnicaDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de Fallas Técnicas.
 */
public class FallaTecnicaController extends ColaboradorRequired {

  private final IncidenteService incidenteService;
  private final HeladeraService heladeraService;
  private final VisitaTecnicaService visitaTecnicaService;

  /**
   * Constructor.
   *
   * @param usuarioService       Servicio de usuario
   * @param colaboradorService   Servicio de colaborador
   * @param incidenteService     Servicio de incidente
   * @param heladeraService      Servicio de heladera
   * @param visitaTecnicaService Servicio de visita técnica
   */
  public FallaTecnicaController(UsuarioService usuarioService,
                                ColaboradorService colaboradorService,
                                IncidenteService incidenteService,
                                HeladeraService heladeraService,
                                VisitaTecnicaService visitaTecnicaService) {
    super(usuarioService, colaboradorService);
    this.incidenteService = incidenteService;
    this.heladeraService = heladeraService;
    this.visitaTecnicaService = visitaTecnicaService;
  }

  /**
   * Muestra la lista de fallas técnicas.
   *
   * @param context Context de Javalin
   */
  public void index(Context context) {
    String filtro = context.queryParamAsClass("filtro", String.class).getOrDefault("todas");

    List<FallaTecnicaDTO> fallasTecnicas = this.incidenteService.buscarTodasFallasTecnicas();
    List<FallaTecnicaDTO> output = switch (filtro) {
      case "resueltas" -> fallasTecnicas.stream()
          .filter(FallaTecnicaDTO::isResuelto).toList();
      case "pendientes" -> fallasTecnicas.stream()
          .filter(f -> !f.isResuelto()).toList();
      default -> fallasTecnicas;
    };

    Map<String, Object> model = new HashMap<>();
    model.put("fallas", output);
    render(context, "incidentes/fallas_tecnicas.hbs", model);
  }

  /**
   * Muestra el detalle de una falla técnica.
   *
   * @param context Context de Javalin
   */
  public void show(Context context) {
    String fallaId = context.pathParam("id");
    Incidente falla = this.incidenteService.buscarIncidentePorId(fallaId);
    Heladera heladera = falla.getHeladera();

    Usuario usuario = usuarioFromSession(context);
    boolean puedeResolver = usuario.getRol().isTecnico() && !falla.getEsResuelta();

    List<VisitaTecnicaDTO> visitasPrevias = this.visitaTecnicaService.buscarPorincidente(falla);

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", HeladeraDTO.preview(heladera));
    model.put("falla", FallaTecnicaDTO.fromIncidente(falla));
    model.put("puedeResolver", puedeResolver);

    model.put("visitasPrevias", visitasPrevias);

    render(context, "incidentes/falla_tecnica_detalle.hbs", model);
  }

  /**
   * Muestra el formulario para crear una falla técnica.
   *
   * @param context Context de Javalin
   */
  public void create(Context context) {
    render(context, "incidentes/falla_tecnica_crear.hbs");
  }

  /**
   * Registra una falla técnica.
   *
   * @param context Context de Javalin
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirect = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      CreateFallaTecnicaDTO nuevaFallaTecnica = new CreateFallaTecnicaDTO(
          context.formParamAsClass("nombre", String.class).get(),
          context.formParamAsClass("descripcion", String.class).get(),
          context.uploadedFile("imagen")
      );

      this.incidenteService.registrarFallaTecnica(colaborador, nuevaFallaTecnica);

      operationSuccess = true;
      redirect.add(new RedirectDTO("/fallas-tecnicas/new", "Reportar otra Falla"));

    } catch (ValidationException | InvalidFormParamException | IOException e) {
      redirect.add(new RedirectDTO("/fallas-tecnicas/new", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirect);

      render(context, "post_result.hbs", model);
    }
  }

}
