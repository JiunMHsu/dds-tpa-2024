package ar.edu.utn.frba.dds.services.usuario;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuario.IUsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

/**
 * Servicio de Usuario.
 */
public class UsuarioService implements WithSimplePersistenceUnit {
  private final IUsuarioRepository usuarioRepository;

  /**
   * Constructor de UsuarioService.
   *
   * @param usuarioRepository Repositorio de Usuario
   */
  public UsuarioService(IUsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  /**
   * Busca un usuario por ID.
   *
   * @param id Id del usuario
   * @return
   */
  public Optional<Usuario> obtenerUsuarioPorId(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("El ID del Usuario no puede ser null o vacío");
    }
    return this.usuarioRepository.buscarPorId(id);
  }

  /**
   * Obtiene un usuario por email.
   *
   * @param email Email del usuario
   * @return
   */
  public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
    return this.usuarioRepository.obtenerPorEmail(email);
  }
}