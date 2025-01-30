package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionDineroDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador de las operaciones de donación de dinero.
 */
public class DonacionDineroController extends ColaboradorRequired {

  private final DonacionDineroService donacionDineroService;

  public DonacionDineroController(DonacionDineroService donacionDineroService,
                                  UsuarioService usuarioService,
                                  ColaboradorService colaboradorService) {
    super(usuarioService, colaboradorService);
    this.donacionDineroService = donacionDineroService;
  }

  /**
   * Muestra el detalle de una donación de dinero.
   * TODO: Revisar
   *
   * @param context Contexto de Javalin
   */
  public void show(Context context) {
    String donacionDineroId = context.pathParam("id");
    Optional<DonacionDinero> donacionDinero = donacionDineroService.buscarPorId(donacionDineroId);

    if (donacionDinero.isEmpty()) {
      throw new ResourceNotFoundException();

    }

    Map<String, Object> model = new HashMap<>();

    DonacionDineroDTO donacion = DonacionDineroDTO.fromColaboracion(donacionDinero.get());
    model.put("donacion_dinero", donacion);

    context.render("colaboraciones/colaboracion_detalle.hbs", model);
  }

  /**
   * Muestra el formulario para crear una donación de dinero.
   * TODO: Revisar
   *
   * @param context Contexto de Javalin
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_DINERO))
      throw new UnauthorizedException("No tiene permiso");

    render(context, "colaboraciones/donacion_dinero_crear.hbs", new HashMap<>());
  }

  /**
   * Guarda una donación de dinero.
   * TODO: Revisar
   *
   * @param context Contexto de Javalin
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      Integer monto = context.formParamAsClass("monto", Integer.class).get();

      String unidadTiempo = context.formParamAsClass("unidad_tiempo", String.class).getOrDefault("NONE");
      Integer periocidad = context.formParamAsClass("periocidad", Integer.class)
          .check(p -> p >= 0, "el periodo debe ser positivo")
          .getOrDefault(0);

      Period frecuencia = switch (unidadTiempo) {
        case "DAY" -> Period.ofDays(periocidad);
        case "WEEK" -> Period.ofWeeks(periocidad);
        case "MONTH" -> Period.ofMonths(periocidad);
        case "YEAR" -> Period.ofYears(periocidad);
        default -> null;
      };

      // TODO - ver como lanzar y manejar fallas por creación y guardado
      DonacionDinero donacionDinero = DonacionDinero.por(colaborador, LocalDateTime.now(), monto);
      this.donacionDineroService.registrar(donacionDinero);

      // TODO - Delegar a Service??
      colaborador.invalidarPuntos();
      this.colaboradorService.actualizar(colaborador);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/colaboraciones", "Colaboraciones"));

    } catch (NotColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (ValidationException v) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }
}
