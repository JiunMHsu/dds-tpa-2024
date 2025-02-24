package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.hacerseCargoHeladera.HacerseCargoHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.HacerseCargoHeladeraService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller de colaboraciones de tipo HacerseCargoHeladera.
 */
public class HacerseCargoHeladeraController extends ColaboradorRequired {

  private final HacerseCargoHeladeraService hacerseCargoHeladeraService;

  /**
   * Constructor de HacerseCargoHeladeraController.
   *
   * @param usuarioService              Servicio de usuario
   * @param colaboradorService          Servicio de colaborador
   * @param hacerseCargoHeladeraService Servicio de colaboraciones de tipo HacerseCargoHeladera
   */
  public HacerseCargoHeladeraController(UsuarioService usuarioService,
                                        ColaboradorService colaboradorService,
                                        HacerseCargoHeladeraService hacerseCargoHeladeraService) {
    super(usuarioService, colaboradorService);
    this.hacerseCargoHeladeraService = hacerseCargoHeladeraService;
  }

  /**
   * Muestra el detalle de una colaboración de tipo HacerseCargoHeladera.
   * TODO: Revisar
   *
   * @param context Context de Javalin
   */
  public void show(Context context) {
    String hacerseCargoHeladeraId = context.pathParam("id");
    HacerseCargoHeladeraDTO dto = hacerseCargoHeladeraService.buscarPorId(hacerseCargoHeladeraId);

    Map<String, Object> model = new HashMap<>();
    model.put("hacerse_cargo_heladera", dto);
    render(context, "colaboraciones/hacerse_cargo_heladera/encargarse_de_heladera_detalle.hbs", model);
  }

  /**
   * Muestra el formulario para crear una colaboración de tipo HacerseCargoHeladera.
   *
   * @param context Context de Javalin
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);
    if (!colaborador.puedeColaborar(TipoColaboracion.HACERSE_CARGO_HELADERA)) {
      throw new UnauthorizedException("No tenes permiso");
    }

    render(context, "colaboraciones/hacerse_cargo_heladera/encargarse_de_heladera_crear.hbs");
  }

  /**
   * Guarda una colaboración de tipo HacerseCargoHeladera.
   *
   * @param context Context de Javalin
   */
  public void save(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.HACERSE_CARGO_HELADERA)) {
      throw new UnauthorizedException("No tenes permiso");
    }

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      this.hacerseCargoHeladeraService.registrar(
          colaborador,
          context.formParamAsClass("heladera", String.class).get()
      );

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));
    } catch (ValidationException | ResourceNotFoundException e) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

}

