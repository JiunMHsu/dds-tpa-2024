package ar.edu.utn.frba.dds.models.repositories.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;

public class CanjeDePuntosRepository implements WithSimplePersistenceUnit {

  public void guardar(CanjeDePuntos canjeDePuntos) {
    entityManager().persist(canjeDePuntos);
  }

  public List<CanjeDePuntos> obtenerPorColaborador(Colaborador unColaborador) {
    return entityManager()
        .createQuery("from CanjeDePuntos c where c.colaborador = :colaborador", CanjeDePuntos.class)
        .setParameter("colaborador", unColaborador)
        .getResultList();
  }

  public Optional<CanjeDePuntos> ultimoPorColaborador(Colaborador colaborador) {
    try {
      return Optional.of(entityManager()
          .createQuery("from CanjeDePuntos c where c.colaborador = :colaborador order by c.fechaHora desc", CanjeDePuntos.class)
          .setParameter("colaborador", colaborador)
          .setMaxResults(1)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  public List<CanjeDePuntos> buscarTodosXColaborador(Colaborador colaborador) {
    return entityManager()
        .createQuery("from CanjeDePuntos c where c.colaborador = :colaborador", CanjeDePuntos.class)
        .setParameter("colaborador", colaborador)
        .getResultList();
  }
}
