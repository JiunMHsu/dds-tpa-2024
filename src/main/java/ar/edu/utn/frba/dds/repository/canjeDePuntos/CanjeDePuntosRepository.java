package ar.edu.utn.frba.dds.repository.canjeDePuntos;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class CanjeDePuntosRepository implements WithSimplePersistenceUnit {

    public void agregar(CanjeDePuntos canjeDePuntos) {
        beginTransaction();
        entityManager().persist(canjeDePuntos);
        commitTransaction();
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
