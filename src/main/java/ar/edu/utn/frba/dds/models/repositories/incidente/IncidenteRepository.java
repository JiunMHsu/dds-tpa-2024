package ar.edu.utn.frba.dds.models.repositories.incidente;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de Incidente.
 */
public class IncidenteRepository implements ICrudRepository<Incidente>, WithSimplePersistenceUnit {

  @Override
  public void guardar(Incidente incidente) {
    entityManager().persist(incidente);
  }

  @Override
  public void actualizar(Incidente incidente) {
    entityManager().merge(incidente);
  }

  @Override
  public void eliminar(Incidente incidente) {
    withTransaction(() -> {
      incidente.setAlta(false);
      entityManager().merge(incidente);
    });
  }

  @Override
  public Optional<Incidente> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Incidente.class, uuid))
          .filter(Incidente::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Incidente> buscarTodos() {
    return entityManager()
        .createQuery("from Incidente i where i.alta = :alta", Incidente.class)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca incidentes por heladera.
   *
   * @param heladera Heladera
   * @return Lista de incidentes
   */
  public List<Incidente> buscarPorHeladera(Heladera heladera) {
    String query = "from Incidente i where i.alta = :alta and i.heladera = :heladera";

    return entityManager()
        .createQuery(query, Incidente.class)
        .setParameter("alta", true)
        .setParameter("heladera", heladera)
        .getResultList();
  }

  /**
   * Busca incidentes por tipo.
   *
   * @param tipo Tipo de incidente
   * @return Lista de incidentes
   */
  public List<Incidente> buscarPorTipo(TipoIncidente tipo) {
    String query = "from Incidente i where i.alta = :alta and i.tipo = :tipo_incidente";

    return entityManager()
        .createQuery(query, Incidente.class)
        .setParameter("alta", true)
        .setParameter("tipo_incidente", tipo)
        .getResultList();
  }

  /**
   * Busca incidentes desde una fecha y hora.
   *
   * @param fechaHora Fecha y hora
   * @return Lista de incidentes
   */
  public List<Incidente> buscarDesde(LocalDateTime fechaHora) {
    String query = "from Incidente i where i.alta = :alta and i.fechaHora >= :fecha_hora";

    return entityManager()
        .createQuery(query, Incidente.class)
        .setParameter("alta", true)
        .setParameter("fecha_hora", fechaHora)
        .getResultList();
  }

  /**
   * Busca alertas.
   *
   * @return Lista de incidentes
   */
  public List<Incidente> buscarAlertas() {
    String query = "from Incidente i where i.alta = :alta and i.tipo != :tipo";

    return entityManager()
        .createQuery(query, Incidente.class)
        .setParameter("alta", true)
        .setParameter("tipo", TipoIncidente.FALLA_TECNICA)
        .getResultList();
  }

}
