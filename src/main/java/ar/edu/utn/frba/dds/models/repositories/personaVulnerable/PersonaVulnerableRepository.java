package ar.edu.utn.frba.dds.models.repositories.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class PersonaVulnerableRepository implements IPersonaVulnerableRepository, WithSimplePersistenceUnit {

    @Override
    public void guardar(PersonaVulnerable personaVulnerable) {
        entityManager().persist(personaVulnerable);
    }

    @Override
    public void actualizar(PersonaVulnerable personaVulnerable) {
        entityManager().merge(personaVulnerable);
    }

    @Override
    public void eliminar(PersonaVulnerable personaVulnerable) {
        withTransaction(() -> {
            personaVulnerable.setAlta(false);
            entityManager().merge(personaVulnerable);
        });
    }

    @Override
    public Optional<PersonaVulnerable> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(PersonaVulnerable.class, uuid))
                    .filter(PersonaVulnerable::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonaVulnerable> buscarTodos() {
        return entityManager()
                .createQuery("from PersonaVulnerable ", PersonaVulnerable.class)
                .getResultList();
    }

    @Override
    public Optional<PersonaVulnerable> buscarPorDocumento(String documento) {
        try {
            return Optional.of(entityManager()
                    .createQuery("from PersonaVulnerable pv where pv.documento.numero = :documento", PersonaVulnerable.class)
                    .setParameter("documento", documento)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

