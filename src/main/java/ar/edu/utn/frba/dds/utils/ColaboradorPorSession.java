package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;
import java.util.Optional;

public abstract class ColaboradorPorSession {

    private final UsuarioService usuarioService;
    private final ColaboradorService colaboradorService;

    public ColaboradorPorSession(UsuarioService usuarioService, ColaboradorService colaboradorService) {
        this.usuarioService = usuarioService;
        this.colaboradorService = colaboradorService;
    }

    public Colaborador obtenerColaboradorPorSession(Context context) {

        String userId = context.sessionAttribute("userId");

        Optional<Usuario> usuarioSession = usuarioService.obtenerUsuarioPorID(userId);
        if (usuarioSession.isEmpty()) {
            context.status(404).result("Usuario no encontrado");
        }
        Optional<Colaborador> colaboradorSession = colaboradorService.obtenerColaboradorPorUsuario(usuarioSession.get());
        if (colaboradorSession.isEmpty()) {
            context.status(404).result("Colaborador no encontrado");
        }

        return colaboradorSession.get();
    }
}
