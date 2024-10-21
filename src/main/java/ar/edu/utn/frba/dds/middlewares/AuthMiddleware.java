package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;
import java.util.Arrays;
import java.util.Set;

public class AuthMiddleware {

    // TODO - ver si se puede mejorar
    // Es medio una negreada porque
    // "/recurso" y "/recurso/*" podría ser una sola
    private static final String[] privateRoutes = new String[]{
            "/home",
            "/heladeras",
            "/heladeras/*",
            "/colaboraciones",
            "/colaboraciones/*",
            "/colaboradores",
            "/colaboradores/*",
    };

    public static void apply(Javalin app) {
        Arrays.stream(privateRoutes).forEach(route -> app.before(route, ctx -> {
            authenticate(ctx);
            authorize(ctx);
        }));
    }

    private static void authenticate(Context context) {
        String userId = userIdFromSession(context);

        if (userId == null)
            throw new UnauthenticatedException("unauthenticated");
    }

    private static void authorize(Context context) {
        TipoRol userRol = rolFromSession(context);

        Set<RouteRole> routeRoles = context.routeRoles();
        if (!routeRoles.isEmpty() && !routeRoles.contains(userRol))
            throw new UnauthorizedException("unauthorized");
    }

    private static TipoRol rolFromSession(Context context) {
        return context.sessionAttribute("userRol") != null
                ? TipoRol.valueOf(context.sessionAttribute("userRol"))
                : null;
    }

    private static String userIdFromSession(Context context) {
        return context.sessionAttribute("userId") != null
                ? context.sessionAttribute("userId")
                : null;
    }

    ;
}
