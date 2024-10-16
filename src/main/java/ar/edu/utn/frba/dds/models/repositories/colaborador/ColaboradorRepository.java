package ar.edu.utn.frba.dds.models.repositories.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ColaboradorRepository implements WithSimplePersistenceUnit {

    public void guardar(Colaborador colaborador) {
        entityManager().persist(colaborador);
    }

    public void actualizar(Colaborador colaborador) {
        withTransaction(() -> entityManager().merge(colaborador));
    }

    public void eliminar(Colaborador colaborador) {
        withTransaction(() -> {
            colaborador.setAlta(false);
            entityManager().merge(colaborador);
        });
    }
    public Optional<Colaborador> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(Colaborador.class, uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    public Optional<Colaborador> buscarPorEmail(String email) {
        return Optional.ofNullable(
                entityManager()
                        .createQuery("from Colaborador c where c.usuario.email = :email", Colaborador.class)
                        .setParameter("email", email)
                        .getSingleResult()
        );
    }

    public List<Colaborador> buscarTodos() {
        return entityManager()
                .createQuery("from Colaborador ", Colaborador.class)
                .getResultList();
    }

    public Optional<Colaborador> buscarPorUsuario(Usuario usuario) {
        try {
            return Optional.of(entityManager()
                .createQuery("from Colaborador c where c.usuario = :usuario and c.alta = :alta", Colaborador.class)
                .setParameter("usuario", usuario)
                .setParameter("alta", true)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
