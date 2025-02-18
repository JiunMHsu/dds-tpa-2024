package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.dtos.heladera.CreateHeladeraDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.heladera.UpdateHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.mapa.MapService;
import ar.edu.utn.frba.dds.services.puntoColocacion.PuntoColocacionService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para las operaciones sobre heladeras.
 */
public class HeladeraController extends ColaboradorRequired implements ICrudViewsHandler {

  private final HeladeraService heladeraService;
  private final PuntoColocacionService puntoColocacionService;
  private final MapService mapService;

  /**
   * Constructor.
   *
   * @param usuarioService         servicio de usuario
   * @param colaboradorService     servicio de colaborador
   * @param heladeraService        servicio de heladera
   * @param puntoColocacionService servicio de punto de colocacion
   * @param mapService             servicio de mapas
   */
  public HeladeraController(UsuarioService usuarioService,
                            ColaboradorService colaboradorService,
                            HeladeraService heladeraService,
                            PuntoColocacionService puntoColocacionService,
                            MapService mapService) {
    super(usuarioService, colaboradorService);
    this.heladeraService = heladeraService;
    this.puntoColocacionService = puntoColocacionService;
    this.mapService = mapService;
  }

  /**
   * Maneja la visualización de la página de heladeras.
   * Este método busca todas las heladeras, convierte cada una a su correspondiente DTO
   * y determina las capacidades del usuario (crear nuevas heladeras, suscribirse a una heladera,
   * encargarse de una heladera). Luego, utiliza estos datos para construir un modelo que se
   * renderiza en una página HTML.
   *
   * @param context  El contexto de la solicitud actual.
   */
  @Override
  public void index(Context context) {

    List<HeladeraDTO> heladeras = this.heladeraService
        .buscarTodas().stream()
        .map(HeladeraDTO::fromHeladra)
        .toList();

    boolean puedeDarDeAlta = rolFromSession(context).isAdmin();
    boolean puedeSuscribirse = rolFromSession(context).isColaborador();
    boolean puedeEncargarseDeHeladera = rolFromSession(context).isColaborador()
        && tipoColaboradorFromSession(context).esJuridico();

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladeras);
    model.put("puedeDarDeAltaHeladera", puedeDarDeAlta);
    model.put("puedeEncargarseDeHeladera", puedeEncargarseDeHeladera);
    model.put("puedeSuscribirseAHeladera", puedeSuscribirse);

    render(context, "heladeras/heladeras.hbs", model);
  }

  /**
   * Maneja la visualización de los detalles de una heladera específica.
   * Este método obtiene la heladera desde la ruta del contexto. Determina si el usuario
   * puede configurar la heladera verificando el rol y tipo de colaborador del usuario en sesión.
   * Luego, utiliza estos datos para construir un modelo que se renderiza en una página HTML de detalles
   * de la heladera.
   *
   * @param context  El contexto de la solicitud actual.
   */
  @Override
  public void show(Context context) {
    Heladera heladera = heladeraFromPath(context);

    boolean puedeConfigurar;
    try {
      Colaborador colaborador = colaboradorFromSession(context);
      puedeConfigurar = heladeraService.puedeConfigurar(colaborador, heladera);
    } catch (NotColaboratorException e) {
      Usuario usuario = usuarioFromSession(context);
      puedeConfigurar = usuario.getRol().isAdmin();
    }

    Map<String, Object> model = new HashMap<>();

    model.put("heladera", HeladeraDTO.fromHeladra(heladera));
    model.put("puedeConfigurar", puedeConfigurar);
    model.put("puedeSuscribirse", this.rolFromSession(context).isColaborador());

    render(context, "heladeras/heladera_detalle.hbs", model);
  }

  /**
   * Maneja la creación de una nueva heladera.
   * Este método extrae la latitud, longitud y radio desde los parámetros de la solicitud.
   * Obtiene las ubicaciones recomendadas basadas en estos parámetros y las agrega al modelo
   * para ser renderizadas en la vista de creación de heladeras.
   *
   * @param context  El contexto de la solicitud actual.
   */
  @Override
  public void create(Context context) {
    try {
      Double latitud = context.queryParamAsClass("lat", Double.class).get();
      Double longitud = context.queryParamAsClass("lon", Double.class).get();
      Integer radio = context.queryParamAsClass("radio", Integer.class)
          .check(rad -> rad >= 0.0, "el radio debe ser positivo").get();

      List<UbicacionDTO> ubicaciones = puntoColocacionService
          .obtenerPuntosDeColocacion(latitud, longitud, radio);

      Map<String, Object> model = new HashMap<>();
      model.put("puntosRecomendados", ubicaciones);
      render(context, "heladeras/heladera_crear.hbs", model);
    } catch (ValidationException e) {
      render(context, "heladeras/heladera_crear.hbs");
    }
  }

  /**
   * Maneja la creación y registro de una nueva heladera en el sistema.
   * Este método extrae los parámetros del formulario para crear un nuevo objeto CreateHeladeraDTO.
   * Si la creación de la heladera es exitosa, se redirige al usuario a la lista de heladeras.
   * En caso de error, se redirige al usuario para reintentar la creación.
   *
   * @param context  El contexto de la solicitud actual.
   */
  @Override
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      CreateHeladeraDTO nuevaHeladera = new CreateHeladeraDTO(
          context.formParamAsClass("nombre", String.class).get(),
          context.formParamAsClass("capacidad", Integer.class)
              .check(c -> c > 0, "la capacidad debe ser positiva").get(),
          context.formParamAsClass("calle", String.class).get(),
          context.formParamAsClass("altura", Integer.class).get(),
          context.formParamAsClass("barrio", String.class).get(),
          context.formParamAsClass("latitud", Double.class).get(),
          context.formParamAsClass("longitud", Double.class).get(),
          context.formParamAsClass("temp_minima", Double.class).get(),
          context.formParamAsClass("temp_maxima", Double.class).get()
      );

      this.heladeraService.registrar(nuevaHeladera);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/heladeras", "Ver Heladeras"));

    } catch (ValidationException e) {
      redirects.add(new RedirectDTO("/heladeras/new", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

  /**
   * Maneja la edición de una heladera específica.
   * Este método obtiene la heladera desde la ruta del contexto. Verifica si el usuario en sesión
   * tiene permiso para configurar la heladera. Si el usuario no tiene los permisos necesarios,
   * lanza una excepción de no autorizado. Luego, construye un modelo de datos y lo renderiza en
   * la vista de edición de la heladera.
   *
   * @param context  El contexto de la solicitud actual.
   * @throws UnauthorizedException si el usuario no tiene permiso para configurar la heladera o
   * ya sea porque no es administrador.
   */
  @Override
  public void edit(Context context) {
    Heladera heladera = heladeraFromPath(context);

    try {
      if (!heladeraService.puedeConfigurar(colaboradorFromSession(context), heladera)) {
        throw new UnauthorizedException("no es encargado de la heladera");
      }
    } catch (NotColaboratorException e) {
      if (!usuarioFromSession(context).getRol().isAdmin()) {
        throw new UnauthorizedException("no es administrador");
      }
    } finally {
      Map<String, Object> model = new HashMap<>();
      model.put("heladera", HeladeraDTO.fromHeladra(heladera));

      render(context, "heladeras/heladera_editar.hbs", model);
    }
  }

  /**
   * Actualiza la configuración de una heladera específica.
   * Este método obtiene la heladera desde la ruta del contexto. Verifica si el usuario en sesión
   * tiene permiso para configurar la heladera. Si el usuario no tiene los permisos necesarios,
   * lanza una excepción de no autorizado. Luego, extrae los parámetros del formulario para
   * actualizar la heladera y construye un modelo de datos para renderizar la vista de resultados
   * de la operación.
   *
   * @param context  El contexto de la solicitud actual.
   * @throws UnauthorizedException si el usuario no tiene permiso para configurar la heladera,
   * ya sea porque no es administrador.
   */
  @Override
  public void update(Context context) {
    Heladera heladera = heladeraFromPath(context);

    try {
      if (!heladeraService.puedeConfigurar(colaboradorFromSession(context), heladera)) {
        throw new UnauthorizedException("no es encargado de la heladera");
      }
    } catch (NotColaboratorException e) {
      if (!usuarioFromSession(context).getRol().isAdmin()) {
        throw new UnauthorizedException("no es administrador");
      }
    }

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      UpdateHeladeraDTO actualizada = new UpdateHeladeraDTO(
          context.formParamAsClass("minima", Double.class).get(),
          context.formParamAsClass("maxima", Double.class).get()
      );

      this.heladeraService.actualizarHeladera(heladera, actualizada);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/heladeras", "Ver Heladeras"));

    } catch (ValidationException e) {
      redirects.add(new RedirectDTO("/heladeras/" + heladera.getId() + "/edit", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      render(context, "post_result.hbs", model);
    }
  }

  @Override
  public void delete(Context context) {
  }

  /**
   * Obtiene el GeoJSON de las heladeras.
   *
   * @param context contexto de la aplicacion
   */
  public void getGeoJson(Context context) {
    List<String> ids = context.queryParams("id");
    List<Heladera> heladeras = new ArrayList<>();

    if (ids.isEmpty()) {
      heladeras = this.heladeraService.buscarTodas();
    } else {
      for (String id : ids) {
        try {
          Heladera heladera = this.heladeraService.buscarPorId(id);
          heladeras.add(heladera);
        } catch (Exception e) {
          // No se hace nada
        }
      }
    }

    String geoJson = this.mapService.generarGeoJson(heladeras);
    context.contentType("application/json");
    context.result(geoJson);
  }

  private Heladera heladeraFromPath(Context context) {
    String heladeraId = context.pathParam("id");
    return this.heladeraService.buscarPorId(heladeraId);
  }
}
