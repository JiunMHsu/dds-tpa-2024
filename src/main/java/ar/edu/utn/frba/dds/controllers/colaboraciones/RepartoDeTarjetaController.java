package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.personaVulnerable.CreatePersonaVulnerableDTO;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
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
 * Controlador de colaboraciones de reparto de tarjetas.
 */
public class RepartoDeTarjetaController extends ColaboradorRequired {

  private final RepartoDeTarjetaService repartoDeTarjetaService;

  /**
   * Constructor para inicializar el controlador de reparto de tarjetas.
   *
   * @param usuarioService         El servicio de usuarios.
   * @param colaboradorService     El servicio de colaboradores.
   * @param repartoDeTarjetaService  El servicio de reparto de tarjetas.
   */
  public RepartoDeTarjetaController(UsuarioService usuarioService,
                                    ColaboradorService colaboradorService,
                                    RepartoDeTarjetaService repartoDeTarjetaService) {
    super(usuarioService, colaboradorService);
    this.repartoDeTarjetaService = repartoDeTarjetaService;
  }

  /**
   * Muestra el detalle de una colaboración de reparto de tarjetas.
   * TODO: Implementar
   *
   * @param context Contexto de Javalin
   */
  public void show(Context context) {
  }

  /**
   * Muestra el formulario de creación de una colaboración de reparto de tarjetas.
   *
   * @param context Contexto de Javalin
   */
  public void create(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    if (!colaborador.puedeColaborar(TipoColaboracion.REPARTO_DE_TARJETAS)) {
      throw new UnauthorizedException("No tenes permiso");
    }

    render(context, "colaboraciones/registro_pv/registro_pv_crear.hbs");
  }

  /**
   * Guarda una colaboración de reparto de tarjetas.
   *
   * @param context Contexto de Javalin
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    Colaborador colaborador = colaboradorFromSession(context);
    if (!colaborador.puedeColaborar(TipoColaboracion.REPARTO_DE_TARJETAS)) {
      throw new UnauthorizedException("No tenes permiso");
    }

    try {
      CreatePersonaVulnerableDTO nuevaPersonaVulnerable = new CreatePersonaVulnerableDTO(
          context.formParamAsClass("nombre", String.class).get(),
          context.formParamAsClass("tipo_documento", String.class).getOrDefault(null),
          context.formParamAsClass("nro_documento", String.class).getOrDefault(null),
          LocalDate.parse(context.formParamAsClass("fecha_nacimiento", String.class).get()),
          context.formParamAsClass("barrio", String.class).get(),
          context.formParamAsClass("calle", String.class).get(),
          context.formParamAsClass("altura", Integer.class).get(),
          context.formParamAsClass("menores_a_cargo", Integer.class).getOrDefault(0),
          context.formParamAsClass("tarjeta", String.class).get()
      );

      this.repartoDeTarjetaService.registrarRepartoTarjeta(colaborador, nuevaPersonaVulnerable);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

    } catch (ValidationException v) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

}
