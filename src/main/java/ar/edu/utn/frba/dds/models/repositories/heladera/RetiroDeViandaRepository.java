package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class RetiroDeViandaRepository implements
        ICrudRepository<RetiroDeVianda>,
        IOperacionPorTarjetaRepository<RetiroDeVianda>,
        WithSimplePersistenceUnit {

    @Override
    public void guardar(RetiroDeVianda retiroDeVianda) {
        withTransaction(() -> entityManager().persist(retiroDeVianda));
    }

    @Override
    public void actualizar(RetiroDeVianda retiroDeVianda) {
        withTransaction(() -> entityManager().merge(retiroDeVianda));
    }

    @Override
    public void eliminar(RetiroDeVianda retiroDeVianda) {
        withTransaction(() -> {
            retiroDeVianda.setAlta(false);
            entityManager().merge(retiroDeVianda);
        });
    }

    @Override
    public Optional<RetiroDeVianda> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(RetiroDeVianda.class, uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<RetiroDeVianda> buscarTodos() {
        return entityManager()
                .createQuery("from RetiroDeVianda", RetiroDeVianda.class)
                .getResultList();
    }

    @Override
    public List<RetiroDeVianda> buscarPorTarjeta(String tarjeta) {
        return entityManager()
                .createQuery("from RetiroDeVianda r " +
                                "where r.tarjetaPersonaVulnerable.codigo = :cod_tarjeta",
                        RetiroDeVianda.class)
                .setParameter("cod_tarjeta", tarjeta)
                .getResultList();
    }

    @Override
    public Optional<RetiroDeVianda> buscarUltimoPorTarjeta(String tarjeta) {
        try {
            return Optional.of(entityManager()
                    .createQuery("from RetiroDeVianda rv " +
                                    "where rv.tarjetaPersonaVulnerable.codigo = :cod_tarjeta " +
                                    "order by rv.fechaHora desc",
                            RetiroDeVianda.class)
                    .setParameter("cod_tarjeta", tarjeta)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
