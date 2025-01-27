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
    String query = switch (solicitud.getOperacion()) {
      case RETIRO_VIANDAS ->
          "from DistribucionViandas d where d.solicitudAperturaOrigen = :s and d.alta = :a";
      case INGRESO_VIANDAS ->
          "from DistribucionViandas d where d.solicitudAperturaDestino = :s and d.alta = :a";
    };
    
    return entityManager()
        .createQuery(query, DistribucionViandas.class)
        .setParameter("s", solicitud)
        .setParameter("a", true)
        .getResultList()
        .stream()
        .findFirst();
  }
}
