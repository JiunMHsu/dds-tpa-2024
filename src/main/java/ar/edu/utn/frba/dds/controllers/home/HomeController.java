package ar.edu.utn.frba.dds.controllers.home;

import ar.edu.utn.frba.dds.permissions.UserRequired;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.HashMap;

/**
 * Controlador de la página de inicio.
 */
public class HomeController extends UserRequired {

  /**
   * Constructor.
   *
   * @param usuarioService Servicio de usuarios
   */
  public HomeController(UsuarioService usuarioService) {
    super(usuarioService);
  }

  /**
   * Muestra la página de inicio.
   *
   * @param context Contexto de Javalin
   */
  public void index(Context context) {
    render(context, "home/home.hbs", new HashMap<>());
  }
}
