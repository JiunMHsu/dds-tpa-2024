package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class SolicitudDeAperturaRepository implements
        ICrudRepository<SolicitudDeApertura>,
        IOperacionPorTarjetaRepository<SolicitudDeApertura>,
        WithSimplePersistenceUnit {

    @Override
    public void guardar(SolicitudDeApertura solicitud) {
        withTransaction(() -> entityManager().persist(solicitud));
    }

    // TODO
    @Override
    public void actualizar(SolicitudDeApertura entidad) {

    }

    // TODO
    @Override
    public void eliminar(SolicitudDeApertura entidad) {

    }

    // TODO
    @Override
    public Optional<SolicitudDeApertura> buscarPorId(String id) {
        return Optional.empty();
    }

    // TODO
    @Override
    public List<SolicitudDeApertura> buscarTodos() {
        return List.of();
    }

    public List<SolicitudDeApertura> obtenerPorTarjeta(TarjetaColaborador tarjeta) {
        return entityManager()
                .createQuery("from SolicitudDeApertura s where s.tarjeta = :tarjeta", SolicitudDeApertura.class)
                .setParameter("tarjeta", tarjeta)
                .getResultList();
    }

    public Optional<SolicitudDeApertura> obtenerUltimoPorTarjeta(TarjetaColaborador tarjeta) {
        return Optional.ofNullable(entityManager()
                .createQuery("from SolicitudDeApertura s where s.tarjeta  = :tarjeta order by s.fechaHora desc", SolicitudDeApertura.class)
                .setParameter("tarjeta", tarjeta)
                .getSingleResult()
        );
    }

    // TODO
    @Override
    public List<SolicitudDeApertura> buscarPorTarjeta(String tarjeta) {
        return List.of();
    }

    // TODO
    @Override
    public Optional<SolicitudDeApertura> buscarUltimoPorTarjeta(String tarjeta) {
        return Optional.empty();
    }
}
