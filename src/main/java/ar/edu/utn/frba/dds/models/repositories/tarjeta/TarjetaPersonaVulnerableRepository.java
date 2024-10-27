package ar.edu.utn.frba.dds.models.repositories.tarjeta;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;
import java.util.UUID;

public class TarjetaPersonaVulnerableRepository implements WithSimplePersistenceUnit {

    public void guardar(TarjetaPersonaVulnerable tarjeta) {
        entityManager().persist(tarjeta);
    }

    public Optional<TarjetaPersonaVulnerable> obtenerPorCodigo(String codigo) {
        return Optional.ofNullable(entityManager()
                .createQuery("from TarjetaPersonaVulnerable t where t.codigo =: codigo", TarjetaPersonaVulnerable.class)
                .setParameter("codigo", codigo)
                .getSingleResult());
    }

    public void eliminar(TarjetaPersonaVulnerable tarjeta) {
        withTransaction(() -> entityManager().remove(tarjeta));
    }

    public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorPersonaId(String personaId) {
        try {
            UUID uuid = UUID.fromString(personaId);
            return Optional.ofNullable(entityManager()
                    .createQuery("from TarjetaPersonaVulnerable t where t.duenio.id = :personaId", TarjetaPersonaVulnerable.class)
                    .setParameter("personaId", uuid)
                    .getSingleResult());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }


}
