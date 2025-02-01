package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.CreateDonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.UpdateDonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de donación de dinero periódica.
 */
public class DonacionDineroPeriodicaController extends ColaboradorRequired {

  private final DonacionDineroService donacionDineroService;

  /**
   * Constructor.
   *
   * @param donacionDineroService servicio de donación de dinero
   */
  public DonacionDineroPeriodicaController(UsuarioService usuarioService,
                                           ColaboradorService colaboradorService,
                                           DonacionDineroService donacionDineroService) {
    super(usuarioService, colaboradorService);
    this.donacionDineroService = donacionDineroService;
  }

  /**
   * Muestra el formulario para crear una donación de dinero periódica.
   * Si el colaborador ya tiene una donación periódica, redirige a la edición.
   *
   * @param context contexto de Javalin
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_DINERO)) {
      throw new UnauthorizedException("No tiene permiso");
    }

    try {
      DonacionPeriodicaDTO donacionPeriodica = donacionDineroService
          .buscarDonacionPeriodica(colaborador);

      String to = "/colaboraciones/donacion-dinero/periodica/"
          + donacionPeriodica.getId() + "/edit";
      context.redirect(to);
    } catch (ResourceNotFoundException e) {
      render(context, "colaboraciones/donacion_dinero/donacion_periodica_crear.hbs");
    }
  }

  /**
   * Guarda una donación de dinero periódica.
   *
   * @param context contexto de Javalin
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      CreateDonacionPeriodicaDTO donacion = new CreateDonacionPeriodicaDTO(
          context.formParamAsClass("monto", Integer.class).get(),
          context.formParamAsClass("periocidad", Integer.class)
              .check(p -> p >= 0, "el periodo debe ser positivo")
              .getOrDefault(0),
          context.formParamAsClass("unidad_tiempo", String.class).getOrDefault("NONE")
      );

      this.donacionDineroService.registrarDonacionPeriodica(colaborador, donacion);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Colaboraciones"));

    } catch (ValidationException | InvalidFormParamException e) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

  /**
   * Muestra el formulario para editar una donación de dinero periódica.
   * TODO: Implementar
   *
   * @param context contexto de Javalin
   */
  public void edit(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);
    String id = context.pathParam("id");
  }

  /**
   * Actualiza una donación de dinero periódica.
   *
   * @param context contexto de Javalin
   */
  public void update(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      UpdateDonacionPeriodicaDTO donacion = new UpdateDonacionPeriodicaDTO(
          context.pathParam("id"),
          context.formParamAsClass("monto", Integer.class).get(),
          context.formParamAsClass("periocidad", Integer.class)
              .check(p -> p >= 0, "el periodo debe ser positivo")
              .getOrDefault(0),
          context.formParamAsClass("unidad_tiempo", String.class).getOrDefault("NONE")
      );

      this.donacionDineroService.actualizarDonacionPeriodica(colaborador, donacion);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Colaboraciones"));

    } catch (ValidationException | InvalidFormParamException e) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

}
