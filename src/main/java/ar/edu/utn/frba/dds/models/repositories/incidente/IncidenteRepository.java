package ar.edu.utn.frba.dds.models.repositories.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

public class IncidenteRepository implements WithSimplePersistenceUnit {
    public void guardar(Incidente incidente) {
        withTransaction(() -> entityManager().persist(incidente));
    }

    public List<Incidente> obtenerTodos() {
        return entityManager()
                .createQuery("from Incidente", Incidente.class)
                .getResultList();
    }

    public List<Incidente> obtenerAPartirDe(LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from Incidente i where i.fechaHora >= :fecha_hora", Incidente.class)
                .setParameter("fecha_hora", fechaHora)
                .getResultList();
    }

    public List<Incidente> obtenerPorTipo(TipoIncidente tipo) {
        return entityManager()
                .createQuery("from Incidente i where i.tipo = :tipo_incidente", Incidente.class)
                .setParameter("tipo_incidente", tipo)
                .getResultList();
    }
}
