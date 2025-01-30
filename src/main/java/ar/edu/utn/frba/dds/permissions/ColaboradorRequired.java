package ar.edu.utn.frba.dds.permissions;

import ar.edu.utn.frba.dds.exceptions.NotColaboratorException;
import ar.edu.utn.frba.dds.exceptions.UnauthenticatedException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import io.javalin.http.Context;

/**
 * Colaborador Required.
 */
public abstract class ColaboradorRequired extends UserRequired {

  protected final ColaboradorService colaboradorService;

  protected ColaboradorRequired(UsuarioService usuarioService,
                                ColaboradorService colaboradorService) {
    super(usuarioService);
    this.colaboradorService = colaboradorService;
  }

  protected Colaborador colaboradorFromSession(Context context)
      throws UnauthenticatedException, UnauthorizedException {

    Usuario usuarioSession = usuarioFromSession(context);
    return colaboradorService
        .obtenerColaboradorPorUsuario(usuarioSession)
        .orElseThrow(UnauthorizedException::new);
  }

  protected TipoColaborador tipoColaboradorFromSession(Context context)
      throws UnauthenticatedException, NotColaboratorException {

    Colaborador colaborador = colaboradorFromSession(context);
    return colaborador.getTipoColaborador();
  }
}
