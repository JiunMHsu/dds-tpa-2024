package ar.edu.utn.frba.dds.models.repositories.reporte;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import com.aspose.pdf.operators.Re;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReporteRepository implements WithSimplePersistenceUnit {
    public void guardar(Reporte reporte) {
        withTransaction(() -> entityManager().persist(reporte));
    }
    public Optional<Reporte> buscarPorId(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(entityManager().find(Reporte.class, uuid))
                    .filter(Reporte::getAlta);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    public List<Reporte> buscarTodos() {
        return entityManager()
                .createQuery("from Reporte", Reporte.class)
                .getResultList();
    }

    public List<Reporte> obtenerAPartirDe(LocalDate fecha) {
        return entityManager()
                .createQuery("from Reporte r where r.fecha >= :fecha", Reporte.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }
}
