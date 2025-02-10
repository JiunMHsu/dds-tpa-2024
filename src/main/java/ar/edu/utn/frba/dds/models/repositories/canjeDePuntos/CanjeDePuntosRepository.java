package ar.edu.utn.frba.dds.models.repositories.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;

/**
 * Repositorio de canje de puntos.
 */
public class CanjeDePuntosRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda un canje de puntos.
   *
   * @param canjeDePuntos canje de puntos
   */
  public void guardar(CanjeDePuntos canjeDePuntos) {
    entityManager().persist(canjeDePuntos);
  }

  /**
   * Busca todos los canjes de puntos de un colaborador.
   *
   * @param unColaborador colaborador
   * @return canjes de puntos del colaborador
   */
  public List<CanjeDePuntos> buscarPorColaborador(Colaborador unColaborador) {
    return entityManager()
        .createQuery("from CanjeDePuntos c where c.colaborador = :colaborador",
            CanjeDePuntos.class)
        .setParameter("colaborador", unColaborador)
        .getResultList();
  }

  /**
   * Busca el último canje de puntos de un colaborador.
   *
   * @param colaborador colaborador
   * @return último canje de puntos del colaborador
   */
  public Optional<CanjeDePuntos> ultimoPorColaborador(Colaborador colaborador) {
    String query = "from CanjeDePuntos c"
        + " where c.colaborador = :colaborador order by c.fechaHora desc";

    try {
      return Optional.of(entityManager()
          .createQuery(query, CanjeDePuntos.class)
          .setParameter("colaborador", colaborador)
          .setMaxResults(1)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

}
