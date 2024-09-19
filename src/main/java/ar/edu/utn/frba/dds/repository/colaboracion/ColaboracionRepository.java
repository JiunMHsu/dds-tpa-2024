package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

public abstract class ColaboracionRepository<T> implements WithSimplePersistenceUnit {

    private final Class<T> type;

    public ColaboracionRepository(Class<T> type) {
        this.type = type;
    }

    public void guardar(T colaboracion) {
        entityManager().persist(colaboracion);
    }

    public List<T> obtenerPorColaborador(Colaborador unColaborador) {
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.colaborador =: id_colaborador", type)
                .setParameter("id_colaborador", unColaborador.getId())
                .getResultList();
    }

    public List<T> obtenerPorColaboradorAPartirDe(Colaborador unColaborador, LocalDateTime fecha) {
        return entityManager()
                .createQuery("from " + type.getName() + " d where d.colaborador = :id_colaborador and d.fechaHora >= :fecha", type)
                .setParameter("id_colaborador", unColaborador.getId())
                .setParameter("fecha", fecha)
                .getResultList();
    }
}

