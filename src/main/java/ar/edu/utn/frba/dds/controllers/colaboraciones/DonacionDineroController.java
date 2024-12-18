package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionDineroDTO;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DonacionDineroController extends ColaboradorRequired implements ICrudViewsHandler {

  private final DonacionDineroService donacionDineroService;

  public DonacionDineroController(DonacionDineroService donacionDineroService,
                                  UsuarioService usuarioService,
                                  ColaboradorService colaboradorService) {

    super(usuarioService, colaboradorService);
    this.donacionDineroService = donacionDineroService;
  }

  @Override
  public void index(Context context) {
    // TODO - Implementar
  }

  @Override
  public void show(Context context) { // TODO - Revisar
    String donacionDineroId = context.pathParam("id");
    Optional<DonacionDinero> donacionDinero = donacionDineroService.buscarPorId(donacionDineroId);

    if (donacionDinero.isEmpty())
      throw new ResourceNotFoundException("No se encontró donacion por dinero paraColaborador id " + donacionDineroId);

    Map<String, Object> model = new HashMap<>();

    DonacionDineroDTO donacionDineroDTO = DonacionDineroDTO.completa(donacionDinero.get());
    model.put("donacion_dinero", donacionDineroDTO);

    context.render("colaboraciones/colaboracion_detalle.hbs", model);
  }

  @Override
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_DINERO))
      throw new UnauthorizedException("No tiene permiso");

    render(context, "colaboraciones/donacion_dinero_crear.hbs", new HashMap<>());
  }

  @Override
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
      DonacionDinero donacionDinero = DonacionDinero.por(colaborador, LocalDateTime.now(), monto, frecuencia);
      this.donacionDineroService.registrar(donacionDinero);

      // TODO - Delegar a Service??
      colaborador.invalidarPuntos();
      this.colaboradorService.actualizar(colaborador);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/colaboraciones", "Colaboraciones"));

    } catch (NonColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (ValidationException v) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      context.render("post_result.hbs", model);
    }
  }

  @Override
  public void edit(Context context) {
  }

  @Override
  public void update(Context context) {
  }

  @Override
  public void delete(Context context) {
  }
}
