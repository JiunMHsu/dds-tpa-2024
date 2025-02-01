package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import java.util.Optional;

/**
 * Repositorio de distribuciones de viandas.
 */
public class DistribucionViandasRepository extends ColaboracionRepository<DistribucionViandas> {

  public DistribucionViandasRepository() {
    super(DistribucionViandas.class);
  }

  /**
   * Busca una distribución de viandas por solicitud de ingreso.
   *
   * @param solicitud la {@link SolicitudDeApertura} asociada
   * @return la distribución, si existe
   */
  public Optional<DistribucionViandas> buscarPorSolicitudDeIngreso(SolicitudDeApertura solicitud) {
    String query = "from DistribucionViandas d"
        + " where d.solicitudAperturaDestino = :s and d.alta = :a";

    return entityManager()
        .createQuery(query, DistribucionViandas.class)
        .setParameter("s", solicitud)
        .setParameter("a", true)
        .getResultList()
        .stream()
        .findFirst();
  }

  /**
   * Busca una distribución de viandas por solicitud de retiro.
   *
   * @param solicitud la {@link SolicitudDeApertura} asociada
   * @return la distribución, si existe
   */
  public Optional<DistribucionViandas> buscarPorSolicitudDeRetiro(SolicitudDeApertura solicitud) {
    String query = "from DistribucionViandas d"
        + " where d.solicitudAperturaOrigen = :s and d.alta = :a";

    return entityManager()
        .createQuery(query, DistribucionViandas.class)
        .setParameter("s", solicitud)
        .setParameter("a", true)
        .getResultList()
        .stream()
        .findFirst();
  }
}
