package ar.edu.utn.frba.dds.repository.tecnico;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class TecnicoRepository implements WithSimplePersistenceUnit {

    public void guardar(Tecnico tecnico) {
        withTransaction(() -> entityManager().persist(tecnico));

    }

    public void actualizar(Tecnico tecnico) {
        withTransaction(() -> entityManager().merge(tecnico));
    }

    public void eliminar(Tecnico tecnico) {
        withTransaction(() -> {
            tecnico.setAlta(false);
            entityManager().merge(tecnico);
        });
    }

    public Optional<Tecnico> obtenerPorCuit(String cuit) {
        return Optional.ofNullable(entityManager()
                .createQuery("from Tecnico t where t.cuit = :cuit and t.alta = :alta", Tecnico.class)
                .setParameter("cuit", cuit)
                .setParameter("alta", true)
                .getSingleResult());
    }


    public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
        return entityManager()
                .createQuery("from Tecnico t where t.areaDeCobertura.barrio = :barrio and t.alta = :alta", Tecnico.class)
                .setParameter("barrio", barrio)
                .setParameter("alta", true)
                .getResultList();
    }

}
