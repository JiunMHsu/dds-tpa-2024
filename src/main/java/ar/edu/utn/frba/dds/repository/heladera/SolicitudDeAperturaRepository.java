package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class SolicitudDeAperturaRepository implements WithSimplePersistenceUnit {

    public void guardar(SolicitudDeApertura solicitud) {
        withTransaction(()-> entityManager().persist(solicitud));
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
