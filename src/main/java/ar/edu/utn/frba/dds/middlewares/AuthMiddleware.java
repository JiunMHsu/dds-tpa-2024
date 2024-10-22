package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import io.javalin.config.RouterConfig;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;
import java.util.Set;

public class AuthMiddleware {

    public static RouterConfig apply(RouterConfig config) {
        return config.mount(router -> router.beforeMatched(ctx -> {
            authenticate(ctx);
            authorize(ctx);
        }));
    }

    private static void authenticate(Context context) {
        Set<RouteRole> routeRoles = context.routeRoles();
        if (routeRoles.isEmpty() || routeRoles.contains(TipoRol.GUEST))
            return;

        String userId = userIdFromSession(context);
        if (userId == null)
            throw new UnauthenticatedException("unauthenticated");
    }

    private static void authorize(Context context) {
        TipoRol userRol = rolFromSession(context);

        Set<RouteRole> routeRoles = context.routeRoles();
        if (routeRoles.isEmpty() || routeRoles.contains(TipoRol.GUEST))
            return;

        if (!routeRoles.contains(userRol))
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
}
