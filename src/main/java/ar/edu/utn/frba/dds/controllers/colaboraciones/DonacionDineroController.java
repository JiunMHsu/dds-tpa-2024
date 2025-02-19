package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de las operaciones de donación de dinero.
 */
public class DonacionDineroController extends ColaboradorRequired {

  private final DonacionDineroService donacionDineroService;

  /**
   * Constructor para inicializar el controlador de donaciones de dinero.
   *
   * @param usuarioService        El servicio de usuarios.
   * @param colaboradorService    El servicio de colaboradores.
   * @param donacionDineroService El servicio de donaciones de dinero.
   */
  public DonacionDineroController(UsuarioService usuarioService,
                                  ColaboradorService colaboradorService,
                                  DonacionDineroService donacionDineroService) {
    super(usuarioService, colaboradorService);
    this.donacionDineroService = donacionDineroService;
  }

  /**
   * Muestra el detalle de una donación de dinero.
   * TODO: Implementar Vista
   *
   * @param context Contexto de Javalin
   */
  public void show(Context context) {
    String donacionDineroId = context.pathParam("id");
    DonacionDineroDTO donacion = donacionDineroService.buscarPorId(donacionDineroId);

    Map<String, Object> model = new HashMap<>();
    model.put("donacionDinero", donacion);
    render(context, "colaboraciones/donacion_dinero/donacion_dinero_detalle.hbs", model);
  }

  /**
   * Muestra el formulario para crear una donación de dinero.
   *
   * @param context Contexto de Javalin
   */
  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_DINERO)) {
      throw new UnauthorizedException("No tiene permiso");
    }

    try {
      DonacionPeriodicaDTO donacionPeriodica = donacionDineroService
          .buscarDonacionPeriodica(colaborador);
      model.put("donacionPeriodica", donacionPeriodica);
    } catch (ResourceNotFoundException e) {
      model.put("donacionPeriodica", null);
    } finally {
      render(context, "colaboraciones/donacion_dinero/donacion_dinero_crear.hbs", model);
    }
  }

  /**
   * Guarda una donación de dinero.
   *
   * @param context Contexto de Javalin
   */
  public void save(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_DINERO)) {
      throw new UnauthorizedException("No tiene permiso");
    }

    donacionDineroService.registrarDonacion(
        colaborador,
        context.formParamAsClass("monto", Integer.class).get()
    );

    List<RedirectDTO> redirects = new ArrayList<>();
    redirects.add(new RedirectDTO("/colaboraciones", "Colaboraciones"));

    Map<String, Object> model = new HashMap<>();
    model.put("success", true);
    model.put("redirects", redirects);
    render(context, "post_result.hbs", model);
  }
}
