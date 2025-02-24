package ar.edu.utn.frba.dds.models.repositories.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio de suscripciones.
 *
 * @param <T> el tipo de suscripción
 */
public abstract class SuscripcionRepository<T> implements WithSimplePersistenceUnit {
  private final Class<T> type;

  public SuscripcionRepository(Class<T> type) {
    this.type = type;
  }

  public void guardar(T suscripcion) {
    entityManager().persist(suscripcion);
  }

  /**
   * Obtiene todas las suscripciones de un colaborador.
   *
   * @return todas las suscripciones del colaborador
   */
  public List<T> obtenerPorColaborador(Colaborador unColaborador) {
    return entityManager()
        .createQuery("from " + type.getName() + " c where c.colaborador = :id_colaborador "
            + "and c.alta = :alta", type)
        .setParameter("id_colaborador", unColaborador.getId())
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Obtiene todas las suscripciones de un colaborador a partir de una fecha y hora.
   *
   * @param fechaHora la fecha y hora
   * @return todas las suscripciones a partir de una fecha y hora
   */
  public List<T> obtenerPorColaboradorDesde(Colaborador unColaborador, LocalDateTime fechaHora) {
    return entityManager()
        .createQuery("from " + type.getName() + " d where d.colaborador = :id_colaborador "
            + "and d.fechaHora >= :fecha and d.alta = :alta", type)
        .setParameter("id_colaborador", unColaborador.getId())
        .setParameter("fecha", fechaHora)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Obtiene todas las suscripciones de una heladera.
   *
   * @param unaHeladera heladera
   * @return todas las suscripciones de la heladera
   */
  public List<T> obtenerPorHeladera(Heladera unaHeladera) {
    return entityManager()
        .createQuery("from " + type.getName() + " h where h.heladera = :heladera "
            + "and h.alta = :alta", type)
        .setParameter("heladera", unaHeladera)
        .setParameter("alta", true)
        .getResultList();
  }

}

