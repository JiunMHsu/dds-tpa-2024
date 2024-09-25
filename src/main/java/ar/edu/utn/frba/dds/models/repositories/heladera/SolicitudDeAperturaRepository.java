package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class SolicitudDeAperturaRepository implements WithSimplePersistenceUnit {

    public void guardar(SolicitudDeApertura solicitud) {
        withTransaction(() -> entityManager().persist(solicitud));
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
}
