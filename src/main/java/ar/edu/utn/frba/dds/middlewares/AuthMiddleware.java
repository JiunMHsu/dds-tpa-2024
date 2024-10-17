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

    private static final String[] privateRoutes = new String[]{
            "/home",
            "/heladeras/*",
            "/colaboraciones/*",
    };

    public static void apply(Javalin app) {
        Arrays.stream(privateRoutes).forEach(route -> app.before(route, AuthMiddleware::verifyAuthorization));
    }

    private static TipoRol rolFromSession(Context context) {
        return context.sessionAttribute("userRol") != null
                ? TipoRol.valueOf(context.sessionAttribute("userRol"))
                : null;
    }

    private static void verifyAuthorization(Context context) {
        TipoRol userRol = rolFromSession(context);

        if (userRol == null)
            throw new UnauthenticatedException("unauthenticated");

        Set<RouteRole> routeRoles = context.routeRoles();
        if (!routeRoles.isEmpty() && !routeRoles.contains(userRol))
            throw new UnauthorizedException("unauthorized");
    }
}
