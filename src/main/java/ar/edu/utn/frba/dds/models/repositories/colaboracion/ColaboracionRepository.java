package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class ColaboracionRepository<T extends EntidadPersistente> implements WithSimplePersistenceUnit {

    private final Class<T> type;

    public ColaboracionRepository(Class<T> type) {
        this.type = type;
    }

    public void guardar(T colaboracion) {
        entityManager().persist(colaboracion);
    }

    public List<T> buscarPorColaborador(Colaborador colaborador) {
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.alta = :alta and c.colaborador = :colaborador", type)
                .setParameter("colaborador", colaborador)
                .setParameter("alta", true)
                .getResultList();
    }

    public List<T> buscarPorColaboradorAPartirDe(Colaborador unColaborador, LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.alta = :alta and c.colaborador = :colaborador and c.fechaHora >= :fecha", type)
                .setParameter("colaborador", unColaborador)
                .setParameter("fecha", fechaHora)
                .setParameter("alta", true)
                .getResultList();
    }

    public List<T> buscarAPartirDe(LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.alta = :alta and c.fechaHora >= :fecha", type)
                .setParameter("fecha", fechaHora)
                .setParameter("alta", true)
                .getResultList();
    }

    public List<T> buscarTodos() {
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.alta = :alta", type)
                .setParameter("alta", true)
                .getResultList();
    }

    public Optional<T> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(type, uuid))
                    .filter(T::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}

