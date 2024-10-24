package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
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

    public Colaborador obtenerColaboradorPorSession(Context context) { // TODO - ver tipo excepcion

        String userId = context.sessionAttribute("userId");

        try {
            Usuario usuarioSession = usuarioService.obtenerUsuarioPorID(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));

            if (usuarioSession.getRol() != TipoRol.COLABORADOR) {
                throw new NonColaboratorException("El usuario con ID: " + userId + " no tiene el rol de colaborador");
            }

            Colaborador colaborador = colaboradorService.obtenerColaboradorPorUsuario(usuarioSession)
                    .orElseThrow(() -> new ResourceNotFoundException("Colaborador no encontrado con Usuario: " + usuarioSession.getNombre()));

            return colaborador;

        } catch (ResourceNotFoundException | NonColaboratorException e) {
            throw e;
        }
    }
}
