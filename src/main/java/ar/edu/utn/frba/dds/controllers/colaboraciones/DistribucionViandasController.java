package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.CreateDistribucionViandasDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DistribucionViandasService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de Distribucion de Viandas.
 */
public class DistribucionViandasController extends ColaboradorRequired {

  private final DistribucionViandasService distribucionViandasService;

  /**
   * Constructor de DistribucionViandasController.
   *
   * @param usuarioService             Servicio de Usuario
   * @param colaboradorService         Servicio de Colaborador
   * @param distribucionViandasService Servicio de Distribucion de Viandas
   */
  public DistribucionViandasController(UsuarioService usuarioService,
                                       ColaboradorService colaboradorService,
                                       DistribucionViandasService distribucionViandasService) {
    super(usuarioService, colaboradorService);
    this.distribucionViandasService = distribucionViandasService;
  }

  /**
   * Recibe de path param el ID de un recurso y
   * devuelve una vista con el detalle de una distribucion de viandas.
   * TODO: Implementar la vista (template).
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void show(Context context) {
    String id = context.pathParam("id");
    DistribucionViandasDTO dto = distribucionViandasService.buscarPorId(id);

    Map<String, Object> model = new HashMap<>();
    model.put("distribucion_viandas", dto);
    render(context, "colaboraciones/colaboracion_detalle.hbs", model);
  }

  /**
   * Devuelve un formulario para dar de alta una nueva distribucion de viandas.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DISTRIBUCION_VIANDAS)) {
      throw new UnauthorizedException("No tiene permiso");
    }

    render(context, "colaboraciones/distribucion_viandas_crear.hbs");
  }

  /**
   * Registra una nueva distribucion de viandas.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      CreateDistribucionViandasDTO input = new CreateDistribucionViandasDTO(
          context.formParamAsClass("origen", String.class).get(),
          context.formParamAsClass("destino", String.class).get(),
          context.formParamAsClass("cantidad", Integer.class).get(),
          context.formParamAsClass("motivo", String.class).get()
      );

      this.distribucionViandasService.registrarNuevaDistribucion(colaborador, input);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

    } catch (NotColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (ValidationException | ResourceNotFoundException | CantidadDeViandasException e) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }
}

