package ar.edu.utn.frba.dds.permissions;

import ar.edu.utn.frba.dds.exceptions.NonTecnicoException;
import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;

/**
 * Clase abstracta que extiende de UserRequired y
 * agrega la funcionalidad de obtener un Tecnico a partir de la sesion.
 */
public abstract class TecnicoRequired extends UserRequired {

  protected final TecnicoService tecnicoService;

  protected TecnicoRequired(UsuarioService usuarioService, TecnicoService tecnicoService) {
    super(usuarioService);
    this.tecnicoService = tecnicoService;
  }

  protected Tecnico tecnicoFromSession(Context context)
      throws UnauthenticatedException, NonTecnicoException {

    Usuario usuarioSession = usuarioFromSession(context);

    return tecnicoService.obtenerTecnicoPorUsuario(usuarioSession)
        .orElseThrow(NonTecnicoException::new);
  }
}
