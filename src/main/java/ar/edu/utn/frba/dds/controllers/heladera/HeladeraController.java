package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.UbicacionDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.puntoIdeal.PuntoIdealService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.AppProperties;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HeladeraController extends ColaboradorRequired implements ICrudViewsHandler {

  private final HeladeraService heladeraService;
  private final PuntoIdealService puntoIdealService;

  public HeladeraController(UsuarioService usuarioService,
                            ColaboradorService colaboradorService,
                            HeladeraService heladeraService,
                            PuntoIdealService puntoIdealService) {
    super(usuarioService, colaboradorService);
    this.heladeraService = heladeraService;
    this.puntoIdealService = puntoIdealService;
  }

  @Override
  public void index(Context context) {
    List<Heladera> heladeras = this.heladeraService.buscarTodas();

    List<HeladeraDTO> heladerasDTO = heladeras.stream()
        .map(HeladeraDTO::preview)
        .toList();


    boolean puedeDarDeAltaHeladera = rolFromSession(context).isAdmin();
    boolean puedeSuscribirseAHeladera = rolFromSession(context).isColaborador();
    boolean puedeEncargarseDeHeladera = rolFromSession(context).isColaborador()
        && tipoColaboradorFromSession(context).esJuridico();

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladerasDTO);
    model.put("puedeDarDeAltaHeladera", puedeDarDeAltaHeladera);
    model.put("puedeEncargarseDeHeladera", puedeEncargarseDeHeladera);
    model.put("puedeSuscribirseAHeladera", puedeSuscribirseAHeladera);

    render(context, "heladeras/heladeras.hbs", model);
  }

  @Override
  public void show(Context context) {
    String heladeraId = context.pathParam("id");

    Heladera heladera = this.heladeraService
        .buscarPorId(heladeraId)
        .orElseThrow(ResourceNotFoundException::new);

    boolean puedeConfigurar;
    try {
      Colaborador colaborador = colaboradorFromSession(context);
      puedeConfigurar = heladeraService.puedeConfigurar(colaborador, heladera);
    } catch (NotColaboratorException e) {
      Usuario usuario = usuarioFromSession(context);
      puedeConfigurar = usuario.getRol().isAdmin();
    }

    Map<String, Object> model = new HashMap<>();

    HeladeraDTO heladeraDTO = HeladeraDTO.completa(heladera);
    model.put("heladera", heladeraDTO);
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

      List<UbicacionDTO> ubicaciones = puntoIdealService
          .obtenerPuntosIdeales(latitud, longitud, radio)
          .stream().map(UbicacionDTO::fromUbicacion).toList();

      Map<String, Object> model = new HashMap<>();
      model.put("puntosRecomendados", ubicaciones);

      render(context, "heladeras/heladera_crear.hbs", model);
    } catch (ValidationException e) {
      render(context, "heladeras/heladera_crear.hbs", new HashMap<>());
    }

  }

  @Override
  public void save(Context context) {
    // TODO - crear e inicializar cliente del broker

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String nombre = context.formParamAsClass("nombre", String.class).get();
      Integer capacidad = context.formParamAsClass("capacidad", Integer.class)
          .check(c -> c > 0, "la capacidad debe ser positiva").get();

      Double latitud = context.formParamAsClass("latitud", Double.class).get();
      Double longitud = context.formParamAsClass("longitud", Double.class).get();

      Direccion direccion = Direccion.con(
          new Barrio(context.formParamAsClass("barrio", String.class).get()),
          new Calle(context.formParamAsClass("calle", String.class).get()),
          context.formParamAsClass("altura", Integer.class).get(),
          new Ubicacion(latitud, longitud)
      );

      Double tempMaxima = context.formParamAsClass("temp_maxima", Double.class).get();
      Double tempMinima = context.formParamAsClass("temp_minima", Double.class).get();
      RangoTemperatura rangoTemperatura = new RangoTemperatura(tempMaxima, tempMinima);

      String topic = AppProperties.getInstance().propertyFromName("BASE_TOPIC")
          + "/heladeras/"
          + nombre.toLowerCase().replace(" ", "-");

      Heladera heladeraNueva = Heladera.con(nombre, direccion, capacidad, rangoTemperatura, topic);
      this.heladeraService.guardarHeladera(heladeraNueva);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/heladeras", "Ver Heladeras"));

    } catch (ValidationException e) {
      redirectDTOS.add(new RedirectDTO("/heladeras/new", "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
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

      HeladeraDTO heladeraDTO = HeladeraDTO.completa(heladera);
      model.put("heladera", heladeraDTO);
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
  public void delete(Context context) { // TODO: ver si es necesario
    Optional<Heladera> posibleHeladeraAEliminar = this.heladeraService.buscarPorId(context.formParam("id"));
    // TODO - chequeo si no existe

    this.heladeraService.eliminarHeladera(posibleHeladeraAEliminar.get());
    context.status(HttpStatus.OK);
    // mostrar algo por exitoso
  }

  Heladera heladeraFromPath(Context context) {
    String heladeraId = context.pathParam("id");
    return this.heladeraService
        .buscarPorId(heladeraId)
        .orElseThrow(ResourceNotFoundException::new);
  }
}
