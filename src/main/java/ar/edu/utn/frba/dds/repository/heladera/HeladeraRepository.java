package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HeladeraRepository implements WithSimplePersistenceUnit {
    
    public void guardar(Heladera heladera) {
        withTransaction(() -> entityManager().persist(heladera));
    }

    public Optional<Heladera> obtenerPorId(UUID id) {
        return Optional.ofNullable(entityManager().find(Heladera.class, id));
    }

    public Optional<Heladera> obtenerPorNombre(String nombre) {
        return Optional.ofNullable((Heladera) entityManager()
                .createQuery("from " + Heladera.class.getName() + " where nombre = :name")
                .setParameter("name", nombre)
                .getSingleResult());
    }

    @SuppressWarnings("unchecked")
    public List<Heladera> obtenerPorBarrio(Barrio barrio) {
        return entityManager()
                .createQuery("from " + Heladera.class.getName() + " where direccion.barrio = :barrio")
                .setParameter("barrio", barrio)
                .getResultList();
    }

}
