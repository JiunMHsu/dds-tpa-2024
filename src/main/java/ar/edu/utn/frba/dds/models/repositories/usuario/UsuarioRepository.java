package ar.edu.utn.frba.dds.models.repositories.usuario;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;
import javax.persistence.NoResultException;

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
        try {
            return Optional.of(entityManager()
                    .createQuery("from Usuario u where u.email = :email and u.alta = :alta", Usuario.class)
                    .setParameter("email", email)
                    .setParameter("alta", true)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Usuario> buscarPorId(String id) {
        try {
            return Optional.of(entityManager()
                .createQuery("from Usuario u where u.id = :id and u.alta = :alta", Usuario.class)
                .setParameter("id", id)
                .setParameter("alta", true)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
