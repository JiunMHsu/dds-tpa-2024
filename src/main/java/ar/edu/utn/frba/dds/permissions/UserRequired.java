package ar.edu.utn.frba.dds.permissions;

import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.Map;

public abstract class UserRequired {

  protected final UsuarioService usuarioService;

  protected UserRequired(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  protected Usuario usuarioFromSession(Context context) throws UnauthenticatedException {
    String userId = context.sessionAttribute("userId");
    return usuarioService.obtenerUsuarioPorID(userId)
        .orElseThrow(() -> new UnauthenticatedException("Usuario no encontrado por ID: " + userId));
  }

  protected TipoRol rolFromSession(Context context) {
    return TipoRol.valueOf(context.sessionAttribute("userRol"));
  }

  protected void render(Context context, String view, Map<String, Object> model) {

    TipoRol sessionRol = TipoRol.valueOf(context.sessionAttribute("userRol"));
    switch (sessionRol) {
      case ADMIN:
        model.put("isAdmin", true);
        break;
      case COLABORADOR:
        model.put("isColaborador", true);
        break;
      case TECNICO:
        model.put("isTecnico", true);
        break;
      default:
        break;
    }

    context.render(view, model);
  }

}
