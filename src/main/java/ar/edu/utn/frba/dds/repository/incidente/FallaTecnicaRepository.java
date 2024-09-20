package ar.edu.utn.frba.dds.repository.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.FallaTecnica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

public class FallaTecnicaRepository implements WithSimplePersistenceUnit {

    public void guardar(FallaTecnica fallaTecnica) {
        withTransaction(() -> entityManager().persist(fallaTecnica));
    }

    public void actualizar(FallaTecnica fallaTecnica) {
        withTransaction(() -> entityManager().merge(fallaTecnica));
    }

    public List<FallaTecnica> obtenerTodas() {
        return entityManager().createQuery("from FallaTecnica", FallaTecnica.class).getResultList();
    }

    public List<FallaTecnica> obtenerAPartirDe(LocalDateTime fechaHora) {
        return entityManager()
                .createQuery("from FallaTecnica ft where ft.fechaHora >= :fecha_hora", FallaTecnica.class)
                .setParameter("fecha_hora", fechaHora)
                .getResultList();
    }

    public List<FallaTecnica> obtenerPorHeladera(Heladera heladera) {
        return entityManager()
                .createQuery("from FallaTecnica ft where ft.heladera = :heladera", FallaTecnica.class)
                .setParameter("heladera", heladera)
                .getResultList();
    }
}
