package ar.edu.utn.frba.dds.repository.usuario;

import ar.edu.utn.frba.dds.models.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class UsuarioRepository implements WithSimplePersistenceUnit {

    public void guardar(Usuario usuario) {
        entityManager().persist(usuario);
    }

    public void actualizar(Usuario usuario) {
        withTransaction(() -> {
            entityManager().merge(usuario);
        });
    }

    public void eliminar(Usuario usuario) {
        withTransaction(() -> {
            usuario.setAlta(false);
            entityManager().merge(usuario);
        });
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return Optional.ofNullable(entityManager()
                .createQuery("from Usuario u where u.email = :email and u.alta = :alta", Usuario.class)
                .setParameter("email", email)
                .setParameter("alta", true)
                .getSingleResult());
    }
}
