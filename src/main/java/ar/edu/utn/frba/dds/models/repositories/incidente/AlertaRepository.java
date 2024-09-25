package ar.edu.utn.frba.dds.models.repositories.incidente;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Alerta;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

public class AlertaRepository implements WithSimplePersistenceUnit {

    public void guardar(Alerta alerta) {
        withTransaction(() -> entityManager().persist(alerta));
    }

    public List<Alerta> obtenerTodas() {
        return entityManager()
                .createQuery("from Alerta", Alerta.class)
                .getResultList();
    }

    public List<Alerta> obtenerAPartirDe(LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from Alerta ft where ft.fechaHora >= :fecha_hora", Alerta.class)
                .setParameter("fecha_hora", fechaHora)
                .getResultList();
    }

    public List<Alerta> obtenerPorHeladera(Heladera heladera) {
        return entityManager()
                .createQuery("from Alerta a where a.heladera = :heladera", Alerta.class)
                .setParameter("heladera", heladera)
                .getResultList();
    }

    public List<Alerta> obtenerPorTipo(TipoIncidente tipoIncidente) {
        return entityManager()
                .createQuery("from Alerta a where a.tipoIncidente = :tipo_incidente", Alerta.class)
                .setParameter("tipo_incidente", tipoIncidente)
                .getResultList();
    }
}
