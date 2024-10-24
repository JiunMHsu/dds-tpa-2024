package ar.edu.utn.frba.dds.controllers.session;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SessionController {

    private final UsuarioService usuarioService;

    public SessionController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
        String forward = context.queryParam("forward");
        if (forward == null) {
            forward = this.getForwardRoute(context);
        }
        Map<String, Object> model = new HashMap<>();

        String email = context.formParam("email");
        String claveIngresada = context.formParam("clave");

        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorEmail(email);

        if (usuario.isEmpty()) {
            model.put("error", "Usuario no encontrado");
            context.status(400).render("login/login.hbs", model);
            return;
        }

        String claveDelUsuario = usuario.get().getContrasenia();

        if (!Objects.equals(claveIngresada, claveDelUsuario)) {
            model.put("error", "Contraseña incorrecta");
            context.status(400).render("login/login.hbs", model);
            return;
        }

        context.sessionAttribute("userId", usuario.get().getId().toString());
        context.sessionAttribute("userRol", usuario.get().getRol().toString());
        context.req().changeSessionId();

        context.redirect(forward);
    }

    public void delete(Context context) {
        HttpSession session = context.req().getSession(false);

        if (session != null) {
            session.invalidate();
        }

        context.redirect("/login");
    }

    private String getForwardRoute(Context context) {
        String forward = context.queryParam("forward");
        System.out.println(forward);
        return forward == null ? "/" : forward;
    }
}
