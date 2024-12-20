package ar.edu.utn.frba.dds.controllers.home;

import ar.edu.utn.frba.dds.permissions.UserRequired;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.HashMap;

public class HomeController extends UserRequired {

  public HomeController(UsuarioService usuarioService) {
    super(usuarioService);
  }

  public void index(Context context) {
    render(context, "home/home.hbs", new HashMap<>());
  }
}
