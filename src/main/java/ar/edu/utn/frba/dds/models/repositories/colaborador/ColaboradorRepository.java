package ar.edu.utn.frba.dds.models.repositories.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;

public class ColaboradorRepository implements IColaboradorRepository, WithSimplePersistenceUnit {

    public void guardar(Colaborador colaborador) {
        entityManager().persist(colaborador);
    }

    public void actualizar(Colaborador colaborador) {
        entityManager().merge(colaborador);
    }

    public void eliminar(Colaborador colaborador) {
        withTransaction(() -> {
            colaborador.setAlta(false);
            entityManager().merge(colaborador);
        });
    }

    @Override
    public Optional<Colaborador> buscarPorId(String id) {
        return Optional.empty();
    }

    @Override
    public List<Colaborador> buscarTodos() {
        return List.of();
    }

    @Override
    public Optional<Colaborador> buscarPorEmail(String email) {
        try {
            return Optional.of(
                    entityManager()
                            .createQuery("from Colaborador c " +
                                            "where c.usuario.email = :email",
                                    Colaborador.class)
                            .setParameter("email", email)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
