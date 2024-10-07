package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.AperturaHeladera;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class AperturaHeladeraRepository implements
        ICrudRepository<AperturaHeladera>,
        IOperacionPorTarjetaRepository<AperturaHeladera>,
        WithSimplePersistenceUnit {

    @Override
    public void guardar(AperturaHeladera apertura) {
        withTransaction(() -> entityManager().persist(apertura));
    }

    @Override
    public void actualizar(AperturaHeladera apertura) {
        withTransaction(() -> entityManager().merge(apertura));
    }

    @Override
    public void eliminar(AperturaHeladera apertura) {
        withTransaction(() -> {
            apertura.setAlta(false);
            entityManager().merge(apertura);
        });
    }

    @Override
    public Optional<AperturaHeladera> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(AperturaHeladera.class, uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<AperturaHeladera> buscarTodos() {
        return entityManager()
                .createQuery("from AperturaHeladera", AperturaHeladera.class)
                .getResultList();
    }

    @Override
    public List<AperturaHeladera> buscarPorTarjeta(String tarjeta) {
        return entityManager()
                .createQuery("from AperturaHeladera ah where ah.tarjetaColaborador.codigo = :cod_tarjeta", AperturaHeladera.class)
                .setParameter("cod_tarjeta", tarjeta)
                .getResultList();
    }

    @Override
    public Optional<AperturaHeladera> buscarUltimoPorTarjeta(String tarjeta) {
        try {
            return Optional.of(entityManager()
                    .createQuery("from AperturaHeladera ah " +
                                    "where ah.tarjetaColaborador.codigo  = :cod_tarjeta " +
                                    "order by ah.fechaHora desc",
                            AperturaHeladera.class)
                    .setParameter("cod_tarjeta", tarjeta)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
