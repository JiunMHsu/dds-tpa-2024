package ar.edu.utn.frba.dds.models.repositories.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
                .createQuery("from " + type.getName() + " c where c.colaborador = :id_colaborador and c.alta = :alta", type)
                .setParameter("id_colaborador", unColaborador.getId())
                .getResultList();
    }

    public List<T> obtenerPorColaboradorId(String id_string) {
        UUID id =  UUID.fromString(id_string);
        return entityManager()
                .createQuery("from " + type.getName() + " c where c.colaborador.usuario.id = :id_usuario and c.alta = :alta", type)
                .setParameter("id_usuario", id_string)
                .setParameter("alta", true)
                .getResultList();
    }

    public List<T> obtenerPorColaboradorAPartirDe(Colaborador unColaborador, LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from " + type.getName() + " d where d.colaborador = :id_colaborador and d.fechaHora >= :fecha", type)
                .setParameter("id_colaborador", unColaborador.getId())
                .setParameter("fecha", fechaHora)
                .getResultList();
    }

    public List<T> buscarTodos() {
        return entityManager()
                .createQuery("from" + type.getName(), type)
                .getResultList();
    }
}

