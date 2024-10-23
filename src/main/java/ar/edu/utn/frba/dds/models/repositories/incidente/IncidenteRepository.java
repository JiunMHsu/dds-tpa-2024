package ar.edu.utn.frba.dds.models.repositories.incidente;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Optional<Incidente> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(Incidente.class, uuid))
                .filter(Incidente::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    public List<Incidente> obtenerIncidentesAlertas() {
        List<Incidente> incidentes = obtenerTodos();
        return incidentes.stream()
            .filter(incidente -> incidente.getTipo() != TipoIncidente.FALLA_TECNICA)
            .collect(Collectors.toList());
    }

}
