package ar.edu.utn.frba.dds.controllers.session;

import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Controlador de sesiones de usuario.
 */
public class SessionController {

  private final UsuarioService usuarioService;

  /**
   * Constructor.
   *
   * @param usuarioService servicio de usuarios
   */
  public SessionController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  /**
   * Muestra el formulario de inicio de sesión.
   *
   * @param context contexto de la aplicación
   */
  public void index(Context context) {
    String forward = this.getForwardRoute(context);

    if (context.sessionAttribute("userId") != null) {
      context.status(HttpStatus.FOUND).redirect(forward);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("forward", forward);
    context.render("login/login.hbs", model);
  }

  /**
   * Crea una nueva sesión de usuario.
   *
   * @param context contexto de la aplicación
   */
  public void create(Context context) {
    String forward = this.getForwardRoute(context);

    try {
      String email = context.formParamAsClass("email", String.class).get();
      String claveIngresada = context.formParamAsClass("clave", String.class).get();

      Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email)
          .orElseThrow(InvalidFormParamException::new);

      String claveDelUsuario = usuario.getContrasenia();
      if (!Objects.equals(claveIngresada, claveDelUsuario)) {
        throw new InvalidFormParamException();
      }

      context.sessionAttribute("userId", usuario.getId().toString());
      context.sessionAttribute("userRol", usuario.getRol().toString());
      context.req().changeSessionId();

      context.redirect(forward);
    } catch (ValidationException | InvalidFormParamException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("isRetry", true);
      model.put("forward", forward);

      context.status(400).render("login/login.hbs", model);
    }
  }

  /**
   * Elimina la sesión de usuario.
   *
   * @param context contexto de la aplicación
   */
  public void delete(Context context) {
    context.req().getSession().invalidate();
    context.redirect("/login");
  }

  /**
   * Obtiene la ruta a la que se redirigirá al usuario después de iniciar sesión.
   *
   * @param context contexto de la aplicación
   * @return ruta a la que se redirigirá al usuario después de iniciar sesión
   */
  private String getForwardRoute(Context context) {
    String forward = context.queryParamAsClass("forward", String.class).getOrDefault("/");
    System.out.println(forward);
    return forward;
  }
}
