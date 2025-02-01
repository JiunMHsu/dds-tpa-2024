package ar.edu.utn.frba.dds.controllers.colaborador;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.TipoColaboracionDTO;
import ar.edu.utn.frba.dds.dtos.colaborador.ColaboradorDTO;
import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.exceptions.ValidationException;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeContrasenias;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
  public ColaboradorController(UsuarioService usuarioService, ColaboradorService colaboradorService) {
    super(usuarioService, colaboradorService);
  }

  /**
   * Devuelve la vista de todos los colaboradores.
   *
   * @param context Objeto Context de io.javalin.http
   */
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();

    List<Colaborador> colaboradores = this.colaboradorService.buscarTodosColaboradores();

    List<ColaboradorDTO> colaboradoresDTO = colaboradores.stream()
        .map(ColaboradorDTO::preview)
        .toList();
    model.put("colaboradores", colaboradoresDTO);

    render(context, "colaboradores/colaboradores.hbs", model);
  }

  public void getProfile(Context context) {
    Colaborador colaborador = colaboradorFromSession(context);

    Map<String, Object> model = new HashMap<>();
    ColaboradorDTO colaboradorDTO = ColaboradorDTO.completa(colaborador);
    model.put("colaborador", colaboradorDTO);

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

  // TODO: REFACTOR
  // Simplificar ambos métodos en uno solo
  public void saveHumana(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String contrasenia = context.formParamAsClass("contrasenia", String.class).get();
      ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

      if (!validador.esValida(contrasenia)) {
        throw new ValidationException("La contraseña no cumple con los requisitos de seguridad.");
      }

      Usuario usuario = Usuario.con(
          context.formParamAsClass("nombre_usuario", String.class).get(),
          contrasenia,
          context.formParamAsClass("email", String.class).get(),
          TipoRol.COLABORADOR
      );
      Direccion direccion = Direccion.con(
          new Barrio(context.formParamAsClass("barrio", String.class).get()),
          new Calle(context.formParamAsClass("calle", String.class).get()),
          Integer.valueOf(context.formParamAsClass("altura", String.class).get())
      );

      Contacto email = Contacto.conEmail(context.formParamAsClass("email", String.class).get());
      Contacto whatsapp = Contacto.conWhatsApp("whatsapp:" + context.formParamAsClass("whatsapp", String.class).get());
      Contacto telegram = Contacto.conTelegram(context.formParamAsClass("telegram", String.class).get());

      List<Contacto> contactos = new ArrayList<>(Arrays.asList(email, whatsapp, telegram));

      List<String> colaboracionesSeleccionadas = context.formParams("colaboraciones");
      ArrayList<TipoColaboracion> formasDeColaborar = new ArrayList<>();

      for (String colaboracion : colaboracionesSeleccionadas) {
        formasDeColaborar.add(TipoColaboracion.valueOf(colaboracion));
      }

      Colaborador colaboradorNuevo = Colaborador.humana(
          usuario,
          context.formParamAsClass("nombre", String.class).get(),
          context.formParamAsClass("apellido", String.class).get(),
          null,
          LocalDate.parse(context.formParamAsClass("fecha_nacimiento", String.class).get()),
          contactos,
          direccion,
          formasDeColaborar,
          new Puntos(0, false, null)
      );

      this.colaboradorService.guardar(colaboradorNuevo);
      operationSuccess = true;

    } catch (ValidationException v) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      render(context, "post_result.hbs", model);
    }
  }

  // TODO: REFACTOR
  public void saveJuridica(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<RedirectDTO> redirectDTOS = new ArrayList<>();
    boolean operationSuccess = false;

    try {
      String contrasenia = context.formParamAsClass("contrasenia", String.class).get();
      ValidadorDeContrasenias validador = new ValidadorDeContrasenias();

      if (!validador.esValida(contrasenia)) {
        throw new ValidationException("La contraseña no cumple con los requisitos de seguridad.");
      }
      Usuario usuario = Usuario.con(
          context.formParamAsClass("nombre_usuario", String.class).get(),
          contrasenia,
          context.formParamAsClass("email", String.class).get(),
          TipoRol.COLABORADOR
      );

      Direccion direccion = Direccion.con(
          new Barrio(context.formParamAsClass("barrio", String.class).get()),
          new Calle(context.formParamAsClass("calle", String.class).get()),
          Integer.valueOf(context.formParamAsClass("altura", String.class).get())
      );

      Contacto email = Contacto.conEmail(context.formParamAsClass("email", String.class).get());
      Contacto whatsapp = Contacto.conWhatsApp(context.formParamAsClass("whatsapp", String.class).get());
      Contacto telegram = Contacto.conTelegram(context.formParamAsClass("telegram", String.class).get());

      List<Contacto> contactos = new ArrayList<>(Arrays.asList(email, whatsapp, telegram));

      List<String> colaboracionesSeleccionadas = context.formParams("colaboraciones");
      ArrayList<TipoColaboracion> formasDeColaborar = new ArrayList<>();

      for (String colaboracion : colaboracionesSeleccionadas) {
        formasDeColaborar.add(TipoColaboracion.valueOf(colaboracion));
      }

      Colaborador colaboradorNuevo = Colaborador.juridica(
          usuario,
          context.formParamAsClass("razon_social", String.class).get(),
          TipoRazonSocial.valueOf(context.formParamAsClass("tipo_razon_social", String.class).get()),
          context.formParamAsClass("rubro", String.class).get(),
          contactos,
          direccion,
          formasDeColaborar,
          new Puntos(0, false, null)
      );

      this.colaboradorService.guardar(colaboradorNuevo);

      operationSuccess = true;

    } catch (ValidationException v) {
      redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
    } finally {
      model.put("success", operationSuccess);
      model.put("redirects", redirectDTOS);
      render(context, "post_result.hbs", model);
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

    List<TipoColaboracionDTO> colaboracionDTOS = formasPermitidas.stream()
        .map(c -> TipoColaboracionDTO.configOption(c, formasRegistradas.contains(c)))
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("id", pathId);
    model.put("colaboraciones", colaboracionDTOS);

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
