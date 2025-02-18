package ar.edu.utn.frba.dds.controllers.suscripcion;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.heladera.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.suscripcion.CreateSuscripcionHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FaltaViandaService;
import ar.edu.utn.frba.dds.services.suscripcion.HeladeraLlenaService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controlador para manejar las suscripciones relacionadas con las heladeras.
 */
public class SuscripcionHeladeraController extends ColaboradorRequired {

  private final HeladeraService heladeraService;
  private final FallaHeladeraService fallaHeladeraService;
  private final FaltaViandaService faltaViandaService;
  private final HeladeraLlenaService heladeraLlenaService;

  /**
   * Constructor para inicializar los servicios necesarios para manejar las suscripciones.
   *
   * @param heladeraService      El servicio de heladeras.
   * @param fallaHeladeraService El servicio de fallas de heladeras.
   * @param faltaViandaService   El servicio de falta de viandas.
   * @param heladeraLlenaService El servicio de heladeras llenas.
   * @param usuarioService       El servicio de usuarios.
   * @param colaboradorService   El servicio de colaboradores.
   */
  public SuscripcionHeladeraController(HeladeraService heladeraService,
                                       FallaHeladeraService fallaHeladeraService,
                                       FaltaViandaService faltaViandaService,
                                       HeladeraLlenaService heladeraLlenaService,
                                       UsuarioService usuarioService,
                                       ColaboradorService colaboradorService) {
    super(usuarioService, colaboradorService);
    this.heladeraService = heladeraService;
    this.fallaHeladeraService = fallaHeladeraService;
    this.faltaViandaService = faltaViandaService;
    this.heladeraLlenaService = heladeraLlenaService;
  }

  /**
   * Crea una suscripción a una heladera específica y renderiza la vista de suscripción.
   *
   * @param context         El contexto de la solicitud actual.
   * @param pathSuscripcion La ruta de la vista de suscripción.
   */
  public void createSuscripcion(Context context, String pathSuscripcion) {

    String heladeraId = context.queryParamAsClass("heladera", String.class).get();

    Heladera heladera = this.heladeraService.buscarPorId(heladeraId);

    Map<String, Object> model = new HashMap<>();

    HeladeraDTO heladeraDto = HeladeraDTO.fromHeladra(heladera);
    model.put("heladera", heladeraDto);

    render(context, "suscripciones/" + pathSuscripcion, model);
  }

  /**
   * Crea una suscripción genérica a una heladera y renderiza la vista de suscripción.
   *
   * @param context El contexto de la solicitud actual.
   */
  public void create(Context context) {
    createSuscripcion(context, "suscripciones.hbs");
  }

  /**
   * Crea una suscripción a las fallas de heladera y renderiza la vista de suscripción de fallas.
   *
   * @param context El contexto de la solicitud actual.
   */
  public void createFallaHeladera(Context context) {
    this.createSuscripcion(context, "suscripcion_falla_heladera.hbs");
  }

  /**
   * Crea una suscripción a la falta de viandas y renderiza la vista de suscripción de falta
   * de viandas.
   *
   * @param context El contexto de la solicitud actual.
   */
  public void createFaltaVianda(Context context) {
    this.createSuscripcion(context, "suscripcion_falta_viandas.hbs");
  }

  /**
   * Crea una suscripción a heladera llena y renderiza la vista de suscripción de heladera llena.
   *
   * @param context El contexto de la solicitud actual.
   */
  public void createHeladeraLlena(Context context) {
    this.createSuscripcion(context, "suscripcion_heladera_llena.hbs");
  }

  /**
   * Guarda una nueva suscripción en el sistema y maneja la redirección en función del éxito de la
   * operación.
   * Este método extrae los parámetros necesarios del formulario y del contexto de la solicitud,
   * crea un nuevo objeto `CreateSuscripcionHeladeraDTO` y lo registra en el servicio
   * correspondiente
   * según el tipo de suscripción (falla técnica, falta de viandas, o heladera llena).
   * Si la operación es exitosa, redirige al usuario a la lista de heladeras. En caso de error,
   * redirige al usuario para que reintente la creación de la suscripción.
   *
   * @param context El contexto de la solicitud actual.
   * @throws UnauthorizedException si el usuario no es colaborador.
   */
  public void save(Context context) {

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDtos = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      CreateSuscripcionHeladeraDTO nuevaSuscripcion = new CreateSuscripcionHeladeraDTO(
          colaboradorFromSession(context),
          this.heladeraService.buscarPorId(context.queryParamAsClass("heladera",
              String.class).get()),
          MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion",
              String.class).get()),
          context.formParamAsClass("contacto", String.class).get()
      );

      String tipoSuscripcion = context.path();

      if (tipoSuscripcion.contains("falla-tecnica")) {
        this.fallaHeladeraService.registrar(nuevaSuscripcion);
      } else if (tipoSuscripcion.contains("falta-viandas")) {
        nuevaSuscripcion.setViandasRestantes(context.formParamAsClass("cantidad-viandas",
            Integer.class).get());
        this.faltaViandaService.registrar(nuevaSuscripcion);
      } else if (tipoSuscripcion.contains("heladera-llena")) {
        nuevaSuscripcion.setEspacioRestante(context.formParamAsClass("viandas-restantes",
            Integer.class).get());
        this.heladeraLlenaService.registrar(nuevaSuscripcion);
      }

      operationSuccess = true;
      redirectDtos.add(new RedirectDTO("/heladeras", "Ir a Heladeras"));

    } catch (NotColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    } catch (ValidationException | ResourceNotFoundException
             | SuscripcionFaltaViandaException | SuscripcionHeladeraLlenaException e) {
      redirectDtos.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDtos);
      render(context, "post_result.hbs", model);
    }

  }

}
