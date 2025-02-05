package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.dtos.heladera.CreateHeladeraDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
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

  @Override
  public void edit(Context context) {
    Heladera heladera = heladeraFromPath(context);

    try {
      Colaborador colaborador = colaboradorFromSession(context);
      if (!heladeraService.puedeConfigurar(colaborador, heladera))
        throw new UnauthorizedException("no es encargado de la heladera");
    } catch (NotColaboratorException e) {
      Usuario usuario = usuarioFromSession(context);
      if (!usuario.getRol().isAdmin())
        throw new UnauthorizedException("no es administrador");
    } finally {
      Map<String, Object> model = new HashMap<>();

      model.put("heladera", HeladeraDTO.fromHeladra(heladera));
      render(context, "heladeras/heladera_editar.hbs", model);
    }
  }

  @Override
  public void update(Context context) {
    Heladera heladera = heladeraFromPath(context);

    try {
      Colaborador colaborador = colaboradorFromSession(context);
      if (!heladeraService.puedeConfigurar(colaborador, heladera))
        throw new UnauthorizedException("no es encargado de la heladera");
    } catch (NotColaboratorException e) {
      Usuario usuario = usuarioFromSession(context);
      if (!usuario.getRol().isAdmin())
        throw new UnauthorizedException("no es administrador");
    }

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      Double nuevoMaximo = context.formParamAsClass("maxima", Double.class).get();
      Double nuevoMinimo = context.formParamAsClass("minima", Double.class).get();

      heladera.setRangoTemperatura(new RangoTemperatura(nuevoMaximo, nuevoMinimo));
      this.heladeraService.actualizarHeladera(heladera);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/heladeras", "Ver Heladeras"));

    } catch (ValidationException e) {
      redirectDTOS.add(new RedirectDTO("/heladeras/" + heladera.getId() + "/edit", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
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
