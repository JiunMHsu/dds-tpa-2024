package ar.edu.utn.frba.dds.models.repositories.usuario;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import java.util.Optional;

/**
 * Interfaz de Repositorio de usuarios.
 */
//TODO check mayuscula
public interface IUsuarioRepository extends ICrudRepository<Usuario> {

  Optional<Usuario> obtenerPorEmail(String mail);
}
