package ar.edu.utn.frba.dds.controllers.session;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.Objects;
import java.util.Optional;

public class SessionController {

    private final UsuarioRepository usuarioRepository;

    public SessionController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void index(Context context) {
        String forward = this.getForwardRoute(context);
        String sessionId = context.cookie("sessionId");

        if (sessionId != null) {
            context.redirect(forward, HttpStatus.FOUND);
            return;
        }

        context.render("login/login.hbs");
    }

    public void create(Context context) {
        String forward = this.getForwardRoute(context);

        String email = context.formParam("email");
        String claveIngresada = context.formParam("clave");

        Optional<Usuario> usuario = usuarioRepository.obtenerPorEmail(email);

        if (usuario.isEmpty()) {
            context.status(400).render("login/login_retry.hbs");
            return;
        }

        String claveDelUsuario = usuario.get().getContrasenia();

        if (!Objects.equals(claveIngresada, claveDelUsuario)) {
            context.status(400).render("login/login_retry.hbs");
            return;
        }

        context.req().changeSessionId();
        context.sessionAttribute("userId", usuario.get().getId());
        context.sessionAttribute("userRol", usuario.get().getRol().getNombre());

        String newSessionId = context.req().getSession().getId();
        context.cookie("sessionId", newSessionId, 3600);

        context.redirect(forward, HttpStatus.OK);
    }

    public void delete(Context context) {
        // borrar la session
    }

    private String getForwardRoute(Context context) {
        String forward = context.queryParam("forward");
        return forward == null ? "/home" : forward;
    }
}
