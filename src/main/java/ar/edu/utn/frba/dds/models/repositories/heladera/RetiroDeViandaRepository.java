package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.RetiroDeVianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Repository de retiros de vianda.
 */
public class RetiroDeViandaRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda un retiro de vianda.
   *
   * @param retiroDeVianda el retiro de vianda a guardar
   */
  public void guardar(RetiroDeVianda retiroDeVianda) {
    entityManager().persist(retiroDeVianda);
  }

  /**
   * Busca un retiro de vianda por id.
   *
   * @param id Id del retiro de vianda
   * @return el retiro de vianda, si existe
   */
  public Optional<RetiroDeVianda> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(RetiroDeVianda.class, uuid))
          .filter(RetiroDeVianda::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca todos los retiros de vianda.
   *
   * @return la lista de retiros de vianda
   */
  public List<RetiroDeVianda> buscarTodos() {
    return entityManager()
        .createQuery("from RetiroDeVianda", RetiroDeVianda.class)
        .getResultList();
  }

  /**
   * Busca los retiros de vianda desde una fecha y hora.
   *
   * @param fechaHora la fecha y hora desde la cual buscar
   * @return la lista de retiros de vianda
   */
  public List<RetiroDeVianda> buscarDesde(@NotNull LocalDateTime fechaHora) {
    String query = "from RetiroDeVianda r where r.alta = :alta and r.fechaHora >= :fecha";

    return entityManager()
        .createQuery(query, RetiroDeVianda.class)
        .setParameter("fecha", fechaHora)
        .setParameter("alta", true)
        .getResultList();
  }
}
