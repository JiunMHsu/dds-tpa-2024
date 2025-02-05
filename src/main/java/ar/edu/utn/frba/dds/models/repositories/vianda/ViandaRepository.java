package ar.edu.utn.frba.dds.models.repositories.vianda;

import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de viandas.
 */
public class ViandaRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda una vianda.
   *
   * @param vianda Vianda a guardar
   */
  public void guardar(Vianda vianda) {
    entityManager().persist(vianda);
  }

  /**
   * Busca una vianda por su id.
   *
   * @param id Id de la vianda
   */
  public Optional<Vianda> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Vianda.class, uuid))
          .filter(Vianda::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca todas las viandas.
   */
  public List<Vianda> buscarTodos() {
    return entityManager()
        .createQuery("from Vianda v where v.alta = :alta", Vianda.class)
        .setParameter("alta", true)
        .getResultList();
  }
}
