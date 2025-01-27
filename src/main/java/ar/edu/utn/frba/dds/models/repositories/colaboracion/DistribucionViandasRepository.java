package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import java.util.Optional;

/**
 * Repositorio de distribuciones de viandas.
 */
public class DistribucionViandasRepository extends ColaboracionRepository<DistribucionViandas> {

  public DistribucionViandasRepository() {
    super(DistribucionViandas.class);
  }

  /**
   * Busca una distribución de viandas por solicitud de apertura.
   *
   * @param solicitud la {@link SolicitudDeApertura} asociada
   * @return la distribución, si existe
   */
  public Optional<DistribucionViandas> buscarPorSolicitudDeApertura(SolicitudDeApertura solicitud) {
    String query = "from DistribucionViandas d"
        + " where d.solicitudDeApertura = :solicitud and d.alta = :alta";

    return entityManager()
        .createQuery(query, DistribucionViandas.class)
        .setParameter("solicitud", solicitud)
        .setParameter("alta", true)
        .getResultList()
        .stream()
        .findFirst();
  }
}
