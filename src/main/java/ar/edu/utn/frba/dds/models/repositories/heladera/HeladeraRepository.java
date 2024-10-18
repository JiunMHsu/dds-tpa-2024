package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class HeladeraRepository implements IHeladeraRepository, WithSimplePersistenceUnit {

    @Override
    public void guardar(Heladera heladera) {
        entityManager().persist(heladera);
    }

    @Override
    public void actualizar(Heladera heladera) {
        withTransaction(() -> entityManager().merge(heladera));
    }

    @Override
    public void eliminar(Heladera heladera) {
        withTransaction(() -> {
            heladera.setAlta(false);
            entityManager().merge(heladera);
        });
    }

    @Override
    public Optional<Heladera> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(Heladera.class, uuid))
                    .filter(Heladera::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Heladera> buscarTodos() {
        return entityManager()
                .createQuery("from Heladera h where h.alta = :alta", Heladera.class)
                .setParameter("alta", true)
                .getResultList();
    }

    @Override
    public Optional<Heladera> buscarPorNombre(String nombre) {
        try {
            return Optional.of(entityManager()
                    .createQuery("from Heladera h where h.alta = :alta and h.nombre = :name", Heladera.class)
                    .setParameter("name", nombre)
                    .setParameter("alta", true)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Heladera> buscarPorBarrio(Barrio barrio) {
        return entityManager()
                .createQuery("from Heladera h where h.alta = :alta and h.direccion.barrio = :barrio", Heladera.class)
                .setParameter("barrio", barrio)
                .setParameter("alta", true)
                .getResultList();
    }

}
