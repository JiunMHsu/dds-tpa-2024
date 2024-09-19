package ar.edu.utn.frba.dds.repository.incidente;

import ar.edu.utn.frba.dds.models.incidente.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

// TODO - Split en 2 repos
public class IncidenteRepository implements WithSimplePersistenceUnit {
    public void agregar(Incidente incidente) {
        entityManager().persist(incidente);
    }

    //

    //    public List<Incidente> obtenerAPartirDe(LocalDateTime fechaHora) {
    //        return entityManager()
    //                .createQuery("from " + Incidente.class.getName() + " d where  d.fechaHora >= :fecha", Incidente.class)
    //                .setParameter("fecha", fechaHora)
    //                .getResultList();
    //    }
}
