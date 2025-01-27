package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import java.util.Optional;

/**
 * Repositorio de donaciones de viandas.
 */
public class DonacionViandaRepository extends ColaboracionRepository<DonacionVianda> {

  public DonacionViandaRepository() {
    super(DonacionVianda.class);
  }

  /**
   * Busca una donación de viandas por solicitud de apertura.
   *
   * @param solicitud la {@link SolicitudDeApertura} asociada
   * @return la donación, si existe
   */
  public Optional<DonacionVianda> buscarPorSolicitudDeApertura(SolicitudDeApertura solicitud) {
    String query = "from DonacionVianda d"
        + " where d.solicitudDeApertura = :solicitud and d.alta = :alta";

    return entityManager()
        .createQuery(query, DonacionVianda.class)
        .setParameter("solicitud", solicitud)
        .setParameter("alta", true)
        .getResultList()
        .stream()
        .findFirst();
  }
}
