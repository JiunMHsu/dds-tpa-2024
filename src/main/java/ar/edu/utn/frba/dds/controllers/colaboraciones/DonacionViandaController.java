package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionVianda.CreateDonacionViandaDTO;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionViandaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de Donación de Viandas.
 */
public class DonacionViandaController extends ColaboradorRequired {

  private final DonacionViandaService donacionViandaService;

  /**
   * Constructor de DonacionViandaController.
   *
   * @param usuarioService        Servicio de Usuario
   * @param colaboradorService    Servicio de Colaborador
   * @param donacionViandaService Servicio de Donación de Viandas
   */
  public DonacionViandaController(UsuarioService usuarioService,
                                  ColaboradorService colaboradorService,
                                  DonacionViandaService donacionViandaService) {
    super(usuarioService, colaboradorService);
    this.donacionViandaService = donacionViandaService;
  }

  /**
   * Muestra el detalle de una donación de vianda.
   * TODO: Implementar
   *
   * @param context Contexto de Javalin
   */
  public void show(Context context) {
    // model.put("donacionVianda", null);
    // render(context, "colaboraciones/donacion_vianda/donacion_vianda_detalle.hbs", model);
  }

  /**
   * Muestra el formulario para crear una donación de vianda.
   * TODO: Revisar y Testear
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.DONACION_VIANDAS)) {
      throw new UnauthorizedException();
    }

    render(context, "colaboraciones/donacion_vianda/donacion_vianda_crear.hbs");
  }

  /**
   * Guarda una donación de vianda.
   * TODO: Revisar y Testear
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void save(Context context) {

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Colaborador colaborador = colaboradorFromSession(context);

      CreateDonacionViandaDTO a = new CreateDonacionViandaDTO(
          context.formParamAsClass("heladera", String.class).get(),
          context.formParamAsClass("comida", String.class).get(),
          context.formParamAsClass("peso", Integer.class).get(),
          context.formParamAsClass("calorias", Integer.class).get(),
          LocalDate.parse(context.formParamAsClass("caducidad", String.class).get())
      );

      this.donacionViandaService.registrar(colaborador, a);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

    } catch (ValidationException | CantidadDeViandasException v) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }
}
