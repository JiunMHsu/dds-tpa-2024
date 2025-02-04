package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.TipoColaboracionDTO;
import ar.edu.utn.frba.dds.dtos.colaborador.ColaboradorDTO;
import ar.edu.utn.frba.dds.dtos.colaborador.CreateColaboradorDTO;
import ar.edu.utn.frba.dds.dtos.usuario.UsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.exceptions.ValidationException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeContrasenias;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller de Colaborador.
 */
public class ColaboradorController extends ColaboradorRequired {

  /**
   * Constructor de ColaboradorController.
   *
   * @param usuarioService     Servicio de Usuario
   * @param colaboradorService Servicio de Colaborador
   */
  public ColaboradorController(UsuarioService usuarioService,
                               ColaboradorService colaboradorService) {
    super(usuarioService, colaboradorService);
  }

  /**
   * Devuelve la vista de todos los colaboradores.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();

    List<Colaborador> todosColaboradores = this.colaboradorService.buscarTodosColaboradores();

    List<ColaboradorDTO> colaboradores = todosColaboradores.stream()
        .map(ColaboradorDTO::preview)
        .toList();
    model.put("colaboradores", colaboradores);

    render(context, "colaboradores/colaboradores.hbs", model);
  }

  /**
   * Devuelve la vista del perfil de un colaborador.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void getProfile(Context context) {
    Colaborador colaboradorSession = colaboradorFromSession(context);

    Map<String, Object> model = new HashMap<>();
    ColaboradorDTO colaborador = ColaboradorDTO.completa(colaboradorSession);
    model.put("colaborador", colaborador);

    render(context, "perfil/perfil.hbs", model);
  }

  /**
   * Devuelve un formulario para dar de alta a un colaborador.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void create(Context context) {
    context.render("signs/sign.hbs");
  }

  /**
   * Registra a un colaborador.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void save(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirects = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String contrasenia = context.formParamAsClass("contrasenia", String.class).get();
      ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

      UsuarioDTO nuevoUsuario;


      if (!validador.esValida(contrasenia)) {
        throw new ValidationException("La contraseña no cumple con los requisitos de seguridad.");
      } else {
        nuevoUsuario = UsuarioDTO.con(
            context.formParamAsClass("nombre_usuario", String.class).get(),
            contrasenia,
            context.formParamAsClass("email", String.class).get(),
            TipoRol.COLABORADOR.toString()
        );

      }

      String tipoParam = context.formParamAsClass("tipo_colaborador", String.class).get();
      TipoColaborador tipo = TipoColaborador.valueOf(tipoParam.toUpperCase());

      String formasDeColaborar = context.formParams("colaboraciones").stream()
          .map(TipoColaboracion::valueOf)
          .map(Enum::name)
          .collect(Collectors.joining(","));

      CreateColaboradorDTO nuevoColaborador;
      if (tipo == TipoColaborador.HUMANO) {

        nuevoColaborador = CreateColaboradorDTO.humana(
            tipoParam,
            context.formParamAsClass("nombre", String.class).get(),
            context.formParamAsClass("apellido", String.class).get(),
            context.formParamAsClass("fecha_nacimiento", String.class).get(),
            context.formParamAsClass("barrio", String.class).get(),
            context.formParamAsClass("calle", String.class).get(),
            context.formParamAsClass("altura", String.class).get(),
            context.formParamAsClass("telefono", String.class).get(),
            formasDeColaborar
        );
      } else {
        nuevoColaborador = CreateColaboradorDTO.juridica(
            tipoParam,
            context.formParamAsClass("razon_social", String.class).get(),
            context.formParamAsClass("tipo_razon_social", String.class).get(),
            context.formParamAsClass("rubro", String.class).get(),
            context.formParamAsClass("barrio", String.class).get(),
            context.formParamAsClass("calle", String.class).get(),
            context.formParamAsClass("altura", String.class).get(),
            context.formParamAsClass("telefono", String.class).get(),
            formasDeColaborar
        );
      }

      this.colaboradorService.registrarNuevoColaborador(nuevoUsuario, nuevoColaborador);

      operationSuccess = true;
      redirects.add(new RedirectDTO("/login", "Iniciar Sesión"));

    } catch (ValidationException e) {
      redirects.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirects);
      context.render("post_result.hbs", model);
    }
  }

  /**
   * Devuelve un formulario para editar las formas de colaborar.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void editFormasDeColaborar(Context context) {
    String pathId = context.pathParam("id");
    Colaborador colaborador = restrictByOwner(context, pathId);

    List<TipoColaboracion> formasRegistradas = colaborador.getFormasDeColaborar();
    System.out.println(formasRegistradas);
    List<TipoColaboracion> formasPermitidas = colaborador.getTipoColaborador()
        .colaboracionesPermitidas();

    List<TipoColaboracionDTO> colaboracion = formasPermitidas.stream()
        .map(c -> TipoColaboracionDTO.configOption(c, formasRegistradas.contains(c)))
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("id", pathId);
    model.put("colaboraciones", colaboracion);

    render(context, "colaboradores/formas_de_colaboracion_editar.hbs", model);
  }

  /**
   * Actualiza las formas de colaborar de un colaborador.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void updateFormasDeColaborar(Context context) {
    Colaborador colaborador = restrictByOwner(context, context.pathParam("id"));

    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      List<String> colaboracionesForm = context.formParams("colaboracion");
      ArrayList<TipoColaboracion> colaboraciones = colaboracionesForm.stream()
          .map(TipoColaboracion::valueOf).collect(Collectors.toCollection(ArrayList::new));

      System.out.println(colaboraciones);

      colaborador.setFormasDeColaborar(colaboraciones);
      this.colaboradorService.actualizar(colaborador);

      operationSuccess = true;
      redirectDTOS.add(new RedirectDTO("/colaboraciones", "Colaborar"));

    } catch (ValidationException e) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      render(context, "post_result.hbs", model);
    }
  }

  private Colaborador restrictByOwner(Context context, String colaboradorId) {
    String userId = context.sessionAttribute("userId");

    Colaborador colaborador = this.colaboradorService.buscarPorId(colaboradorId)
        .orElseThrow(UnauthorizedException::new);

    if (!Objects.equals(colaborador.getUsuario().getId().toString(), userId)) {
      throw new UnauthorizedException();
    }

    return colaborador;
  }

  /**
   * Registra el chatID de Telegram al colaborador.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void registrarChatId(Context context) {

    try {
      Colaborador colaborador = colaboradorFromSession(context);
      String chatId = context.queryParamAsClass("chatId", String.class).get();

      this.colaboradorService.registrarChatId(colaborador, chatId);

    } catch (NotColaboratorException e) {
      throw new UnauthorizedException(e.getMessage());
    }
  }
}
