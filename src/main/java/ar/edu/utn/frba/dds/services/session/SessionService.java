package ar.edu.utn.frba.dds.services.session;

import io.javalin.http.Context;

import java.util.Optional;

public class SessionService {
    public Optional<String> obtenerColaboradorID(Context context) {
        return Optional.ofNullable(context.sessionAttribute("userId"));
    }
}
