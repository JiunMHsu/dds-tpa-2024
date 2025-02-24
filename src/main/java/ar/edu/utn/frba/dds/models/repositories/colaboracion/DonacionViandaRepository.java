package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import java.time.LocalDateTime;
import java.util.List;
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

  /**
   * Busca donaciones de viandas por colaborador desde una fecha y hora.
   *
   * @param colaborador el {@link Colaborador} asociado
   * @param fechaHora   la fecha y hora desde la cual buscar
   * @return las donaciones, si existen
   */
  public List<DonacionVianda> bucarCompletadaPorColaboradorDesde(Colaborador colaborador,
                                                                 LocalDateTime fechaHora) {
    return this.buscarPorColaboradorDesde(colaborador, fechaHora)
        .stream()
        .filter(DonacionVianda::getEsEntregada)
        .toList();
  }
}
