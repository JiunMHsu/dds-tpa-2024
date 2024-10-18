package ar.edu.utn.frba.dds.models.repositories.usuario;

import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class UsuarioRepository implements IUsuarioRepository, WithSimplePersistenceUnit {

    public void guardar(Usuario usuario) {
        entityManager().persist(usuario);
    }

    public void actualizar(Usuario usuario) {
        entityManager().merge(usuario);
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

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(Usuario.class, uuid))
                    .filter(Usuario::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        return entityManager()
                .createQuery("from Usuario ", Usuario.class)
                .getResultList();
    }
}
