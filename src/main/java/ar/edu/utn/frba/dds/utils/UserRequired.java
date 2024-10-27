package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.Map;

public abstract class UserRequired {

    protected final UsuarioService usuarioService;
    protected final ColaboradorService colaboradorService;

    protected UserRequired(UsuarioService usuarioService, ColaboradorService colaboradorService) {
        this.usuarioService = usuarioService;
        this.colaboradorService = colaboradorService;
    }

    protected Colaborador colaboradorFromSession(Context context) throws UnauthenticatedException, NonColaboratorException {
        String userId = context.sessionAttribute("userId");

        Usuario usuarioSession = usuarioService.obtenerUsuarioPorID(userId)
                .orElseThrow(() -> new UnauthenticatedException("Usuario no encontrado con ID: " + userId));

        return colaboradorService.obtenerColaboradorPorUsuario(usuarioSession)
                .orElseThrow(() -> new NonColaboratorException("Colaborador no encontrado con Usuario: " + usuarioSession.getNombre()));
    }

    protected Usuario usuarioFromSession(Context context) throws UnauthenticatedException {
        String userId = context.sessionAttribute("userId");
        return usuarioService.obtenerUsuarioPorID(userId)
                .orElseThrow(() -> new UnauthenticatedException("Usuario no encontrado con ID: " + userId));
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
