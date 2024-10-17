package ar.edu.utn.frba.dds.controllers.session;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SessionController {

    private final UsuarioRepository usuarioRepository;

    public SessionController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void index(Context context) {
        String forward = this.getForwardRoute(context);

        if (context.sessionAttribute("userId") != null) {
            context.status(HttpStatus.FOUND).redirect(forward);
            return;
        }

        context.render("login/login.hbs");
    }

    public void create(Context context) {
        // TODO - no se obtiene query param
        // El forward en este caso atrapa la ruta del post (la que el formulario envía)
        // El formulario siempre envía sin query params, entonces siempre va a ser NULL
        String forward = this.getForwardRoute(context);
        Map<String, Object> model = new HashMap<>();

        String email = context.formParam("email");
        String claveIngresada = context.formParam("clave");

        Optional<Usuario> usuario = usuarioRepository.obtenerPorEmail(email);

        // TODO - modificar la estrategia de manejar falla login
        if (usuario.isEmpty()) {
            model.put("isRetry", true);
            context.status(400).render("login/login.hbs", model);
            return;
        }

        String claveDelUsuario = usuario.get().getContrasenia();

        if (!Objects.equals(claveIngresada, claveDelUsuario)) {
            model.put("isRetry", true);
            context.status(400).render("login/login.hbs", model);
            return;
        }

        context.sessionAttribute("userId", usuario.get().getId().toString());
        context.sessionAttribute("userRol", usuario.get().getRol().toString());
        context.req().changeSessionId();

        context.redirect(forward);
    }

    public void delete(Context context) {
        // borrar la session
    }

    private String getForwardRoute(Context context) {
        String forward = context.queryParam("forward");
        System.out.println(forward);
        return forward == null ? "/" : forward;
    }
}
