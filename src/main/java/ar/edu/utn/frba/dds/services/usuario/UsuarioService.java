package ar.edu.utn.frba.dds.services.usuario;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuario.IUsuarioRepository;

import java.util.Optional;

public class UsuarioService {
  private final IUsuarioRepository usuarioRepository;

  public UsuarioService(IUsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public Optional<Usuario> obtenerUsuarioPorID(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("El ID del Usuario no puede ser null o vacío");
    }
    return this.usuarioRepository.buscarPorId(id);
  }

  public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
    return this.usuarioRepository.obtenerPorEmail(email);
  }
}