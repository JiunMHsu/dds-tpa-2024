package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de solicitudes de apertura.
 */
public class SolicitudDeAperturaRepository
    implements ICrudRepository<SolicitudDeApertura>, WithSimplePersistenceUnit {

  @Override
  public void guardar(SolicitudDeApertura solicitud) {
    withTransaction(() -> entityManager().persist(solicitud));
  }

  @Override
  public void actualizar(SolicitudDeApertura solicitud) {
    entityManager().merge(solicitud);
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
      return Optional.ofNullable(entityManager().find(SolicitudDeApertura.class, uuid))
          .filter(SolicitudDeApertura::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<SolicitudDeApertura> buscarTodos() {
    return entityManager()
        .createQuery("from SolicitudDeApertura s where s.alta = :alta", SolicitudDeApertura.class)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca las solicitudes de apertura de una tarjeta en una heladera.
   *
   * @param tarjeta  Código de la tarjeta
   * @param heladera Heladera
   * @return Lista de solicitudes de apertura
   */
  public List<SolicitudDeApertura> buscarPorTarjetaHeladera(String tarjeta, Heladera heladera) {
    String query = "from SolicitudDeApertura s"
        + " where s.tarjeta.codigo = :cod_tarjeta and s.heladera = :heladera and s.alta = :alta";

    return entityManager()
        .createQuery(query, SolicitudDeApertura.class)
        .setParameter("cod_tarjeta", tarjeta)
        .setParameter("heladera", heladera)
        .setParameter("alta", true)
        .getResultList();
  }
}
