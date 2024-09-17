package ar.edu.utn.frba.dds.repository.canjeDePuntos;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import java.util.List;
import lombok.Getter;

@Getter
public class CanjeDePuntosRepository implements WithSimplePersistenceUnit {

  public void agregar(CanjeDePuntos canjeDePuntos) {
    entityManager().persist(canjeDePuntos);
  }

  @SuppressWarnings("unchecked")
  public List<CanjeDePuntos> obtenerPorColaborador(Colaborador unColaborador) {
    return entityManager()
            .createQuery("from CanjeDePuntos c where c.colaborador =: id_colaborador")
            .setParameter("id_colaborador", unColaborador.getId())
            .getResultList();
  }

  public CanjeDePuntos obtenerUltimoPorColaborador(Colaborador unColaborador) {
    return entityManager()
            .createQuery("from CanjeDePuntos c where c.colaborador = :id_colaborador order by c.fechaCanjeo desc", CanjeDePuntos.class)
            .setParameter("id_colaborador", unColaborador.getId())
            .setMaxResults(1)
            .getSingleResult();
  }



}
