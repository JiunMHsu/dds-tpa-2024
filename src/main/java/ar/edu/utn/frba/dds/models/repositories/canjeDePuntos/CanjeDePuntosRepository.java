package ar.edu.utn.frba.dds.models.repositories.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.puntosPorColaborador.CanjeDePuntos;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

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

    public Optional<CanjeDePuntos> obtenerUltimoPorColaborador(Colaborador unColaborador) {
        try {
            return Optional.of(
                    entityManager()
                            .createQuery("from CanjeDePuntos c where c.colaborador = :colaborador order by c.fechaCanjeo desc", CanjeDePuntos.class)
                            .setParameter("colaborador", unColaborador)
                            .setMaxResults(1)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
