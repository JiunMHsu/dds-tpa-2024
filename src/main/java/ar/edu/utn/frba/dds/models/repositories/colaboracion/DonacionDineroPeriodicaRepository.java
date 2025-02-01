package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDineroPeriodica;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de donaciones de dinero periódicas.
 */
public class DonacionDineroPeriodicaRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda una donación de dinero periódica.
   *
   * @param donacion donación de dinero periódica
   */
  public void guardar(DonacionDineroPeriodica donacion) {
    entityManager().persist(donacion);
  }

  /**
   * Actualiza una donación de dinero periódica.
   *
   * @param donacion donación de dinero periódica
   */
  public void actualizar(DonacionDineroPeriodica donacion) {
    entityManager().merge(donacion);
  }

  /**
   * Busca todas las donaciones de dinero periódicas.
   *
   * @return lista de donaciones de dinero periódicas
   */
  public List<DonacionDineroPeriodica> buscarTodos() {
    return entityManager()
        .createQuery(
            "from DonacionDineroPeriodica d where d.alta = :alta",
            DonacionDineroPeriodica.class)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca una donación de dinero periódica por su ID.
   *
   * @param id Id de la donación de dinero periódica
   * @return donación de dinero periódica
   */
  public Optional<DonacionDineroPeriodica> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(DonacionDineroPeriodica.class, uuid))
          .filter(DonacionDineroPeriodica::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca una donación de dinero periódica por su colaborador.
   *
   * @param colaborador Colaborador de la donación de dinero periódica
   * @return donación de dinero periódica
   */
  public Optional<DonacionDineroPeriodica> buscarPorColaborador(Colaborador colaborador) {
    String query = "from DonacionDineroPeriodica d "
        + "where d.colaborador = :colaborador and d.alta = :alta";

    return entityManager()
        .createQuery(query, DonacionDineroPeriodica.class)
        .setParameter("colaborador", colaborador)
        .setParameter("alta", true)
        .getResultList().stream().findFirst();
  }
}
