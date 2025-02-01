package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de colaboraciones.
 *
 * @param <T> el tipo de colaboración
 */
public abstract class ColaboracionRepository<T extends EntidadPersistente>
    implements WithSimplePersistenceUnit {

  private final Class<T> type;

  /**
   * Constructor.
   *
   * @param type el tipo de colaboración
   */
  public ColaboracionRepository(Class<T> type) {
    this.type = type;
  }

  /**
   * Guarda una colaboración.
   *
   * @param colaboracion la colaboración a guardar
   */
  public void guardar(T colaboracion) {
    entityManager().persist(colaboracion);
  }

  public void actualizar(T colaboracion) {
    entityManager().merge(colaboracion);
  }

  /**
   * Busca colaboraciones por colaborador.
   *
   * @param colaborador un {@link Colaborador}
   * @return las colaboraciones, si existen
   */
  public List<T> buscarPorColaborador(Colaborador colaborador) {
    return entityManager()
        .createQuery("from "
            + type.getName()
            + " c where c.alta = :alta and c.colaborador = :colaborador", type)
        .setParameter("colaborador", colaborador)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca colaboraciones por colaborador y fecha.
   *
   * @param colaborador un {@link Colaborador}
   * @param fechaHora   la fecha y hora a partir de la cual buscar
   * @return las colaboraciones, si existen
   */
  public List<T> buscarPorColaboradorDesde(Colaborador colaborador, LocalDateTime fechaHora) {
    String query = "from "
        + type.getName()
        + " c where c.alta = :alta and c.colaborador = :colaborador and c.fechaHora >= :fecha";

    return fechaHora == null
        ? buscarPorColaborador(colaborador)
        : entityManager()
        .createQuery(query, type)
        .setParameter("colaborador", colaborador)
        .setParameter("fecha", fechaHora)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca colaboraciones a partir de una fecha y hora.
   *
   * @param fechaHora la fecha y hora a partir de la cual buscar
   * @return las colaboraciones, si existen
   */
  public List<T> buscarDesde(LocalDateTime fechaHora) {
    String query = "from " + type.getName() + " c where c.alta = :alta and c.fechaHora >= :fecha";

    return fechaHora == null
        ? buscarTodos()
        : entityManager()
        .createQuery(query, type)
        .setParameter("fecha", fechaHora)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca todas las colaboraciones.
   *
   * @return todas las colaboraciones
   */
  public List<T> buscarTodos() {
    return entityManager()
        .createQuery("from " + type.getName() + " c where c.alta = :alta", type)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca una colaboración por ID.
   *
   * @param id Id de la colaboración
   * @return la colaboración, si existe
   */
  public Optional<T> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(type, uuid))
          .filter(T::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}

