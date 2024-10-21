package ar.edu.utn.frba.dds.models.repositories.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

public abstract class SuscripcionRepository<T> implements WithSimplePersistenceUnit {
    private final Class<T> type;

    public SuscripcionRepository(Class<T> type) {
        this.type = type;
    }

    public void guardar(T suscripcion) {
        entityManager().persist(suscripcion);
    }

    public List<T> obtenerPorColaborador(Colaborador unColaborador) {
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.colaborador = :id_colaborador and c.alta = :alta", type)
                .setParameter("id_colaborador", unColaborador.getId())
                .setParameter("alta", true)
                .getResultList();
    }

    public List<T> obtenerPorColaboradorAPartirDe(Colaborador unColaborador, LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from " + type.getName() + " d where d.colaborador = :id_colaborador and d.fechaHora >= :fecha and d.alta = :alta", type)
                .setParameter("id_colaborador", unColaborador.getId())
                .setParameter("fecha", fechaHora)
                .setParameter("alta", true)
                .getResultList();
    }

    public List<T> obtenerPorHeladera(Heladera unaHeladera) {
        return entityManager()
                .createQuery("from " + type.getName() + " h where h.heladera = :id_heladera and h.alta = :alta", type)
                .setParameter("id_heladera", unaHeladera.getId())
                .setParameter("alta", true)
                .getResultList();
    }

    // medio un choreo estas clases jiji

}

