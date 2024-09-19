package ar.edu.utn.frba.dds.repository.usuario;

import ar.edu.utn.frba.dds.models.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class UsuarioRepository implements WithSimplePersistenceUnit {

    public void guardar(Usuario usuario) {
        entityManager().persist(usuario);
    }

    // TODO - Update

    // TODO - Remove

    // TODO - Obtener por email
}
