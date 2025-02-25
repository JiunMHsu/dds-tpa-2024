package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

/**
 * Repositorio de Heladera.
 */
public class HeladeraRepository implements ICrudRepository<Heladera>, WithSimplePersistenceUnit {

  @Override
  public void guardar(Heladera heladera) {
    entityManager().persist(heladera);
  }

  @Override
  public void actualizar(Heladera heladera) {
    entityManager().merge(heladera);
  }

  @Override
  public void eliminar(Heladera heladera) {
    withTransaction(() -> {
      heladera.setAlta(false);
      entityManager().merge(heladera);
    });
  }

  @Override
  public Optional<Heladera> buscarPorId(String id) {
    entityManager().clear();

    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Heladera.class, uuid))
          .filter(Heladera::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<Heladera> buscarTodos() {
    entityManager().clear();

    return entityManager()
        .createQuery("from Heladera h where h.alta = :alta", Heladera.class)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca una heladera por su nombre.
   *
   * @param nombre Nombre de la heladera
   * @return Heladera
   */
  public Optional<Heladera> buscarPorNombre(String nombre) {
    entityManager().clear();

    try {
      return Optional.of(entityManager()
          .createQuery("from Heladera h where h.alta = :alta and h.nombre = :name", Heladera.class)
          .setParameter("name", nombre)
          .setParameter("alta", true)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca las heladeras por barrio.
   *
   * @param barrio Barrio
   * @return Lista de heladeras
   */
  public List<Heladera> buscarPorBarrio(Barrio barrio) {
    entityManager().clear();
    String query = "from Heladera h where h.alta = :alta and h.direccion.barrio = :barrio";

    return entityManager()
        .createQuery(query, Heladera.class)
        .setParameter("barrio", barrio)
        .setParameter("alta", true)
        .getResultList();
  }

}
