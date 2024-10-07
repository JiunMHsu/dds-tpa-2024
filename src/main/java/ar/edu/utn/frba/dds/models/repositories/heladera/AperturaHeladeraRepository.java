package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.AperturaHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;

public class AperturaHeladeraRepository implements IOperacionPorTarjetaRepository<AperturaHeladera>, WithSimplePersistenceUnit {

    @Override
    public void guardar(AperturaHeladera apertura) {
        withTransaction(() -> entityManager().persist(apertura));
    }

    // TODO
    @Override
    public void actualizar(AperturaHeladera entidad) {

    }

    // TODO
    @Override
    public void eliminar(AperturaHeladera entidad) {

    }

    // TODO
    @Override
    public Optional<AperturaHeladera> buscarPorId(String id) {
        return Optional.empty();
    }

    // TODO
    @Override
    public List<AperturaHeladera> buscarTodos() {
        return List.of();
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
                    .createQuery(
                            "from AperturaHeladera ah " +
                                    "where ah.tarjetaColaborador.codigo  = :cod_tarjeta " +
                                    "order by ah.fechaHora desc",
                            AperturaHeladera.class)
                    .setParameter("cod_tarjeta", tarjeta)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
