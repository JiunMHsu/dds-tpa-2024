package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class SolicitudDeAperturaRepository implements
        ICrudRepository<SolicitudDeApertura>,
        IOperacionPorTarjetaRepository<SolicitudDeApertura>,
        WithSimplePersistenceUnit {

    @Override
    public void guardar(SolicitudDeApertura solicitud) {
        withTransaction(() -> entityManager().persist(solicitud));
    }

    @Override
    public void actualizar(SolicitudDeApertura solicitud) {
        withTransaction(() -> entityManager().merge(solicitud));

    }

    @Override
    public void eliminar(SolicitudDeApertura solicitud) {
        withTransaction(() -> {
            solicitud.setAlta(false);
            entityManager().merge(solicitud);
        });
    }

    @Override
    public Optional<SolicitudDeApertura> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(SolicitudDeApertura.class, uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<SolicitudDeApertura> buscarTodos() {
        return entityManager()
                .createQuery("from SolicitudDeApertura", SolicitudDeApertura.class)
                .getResultList();
    }

    @Override
    public List<SolicitudDeApertura> buscarPorTarjeta(String tarjeta) {
        return entityManager()
                .createQuery("from SolicitudDeApertura s " +
                                "where s.tarjeta.codigo = :cod_tarjeta",
                        SolicitudDeApertura.class)
                .setParameter("cod_tarjeta", tarjeta)
                .getResultList();
    }

    @Override
    public Optional<SolicitudDeApertura> buscarUltimoPorTarjeta(String tarjeta) {
        try {
            return Optional.of(entityManager()
                    .createQuery("from SolicitudDeApertura sa " +
                                    "where sa.tarjeta.codigo = :cod_tarjeta " +
                                    "order by sa.fechaHora desc",
                            SolicitudDeApertura.class)
                    .setParameter("cod_tarjeta", tarjeta)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
