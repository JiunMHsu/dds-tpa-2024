package ar.edu.utn.frba.dds.repository.tecnico;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class TecnicoRepository implements WithSimplePersistenceUnit {

    public void guardar(Tecnico tecnico) {
        entityManager().persist(tecnico);
    }

    public void actualizar(Tecnico tecnico) {
        withTransaction(() -> {
            entityManager().merge(tecnico);
        });
    }

    public void eliminar(Tecnico tecnico) {
        tecnico.setAlta(false);
        entityManager().merge(tecnico);
    }

    public Tecnico obtenerPorCuit(String cuit) {
        return entityManager()
                .createQuery("from Tecnico t where t.cuit =: cuit", Tecnico.class)
                .setParameter("cuit", cuit)
                .getSingleResult();
    }


    public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
        return entityManager()
                .createQuery("from Tecnico t where t.areaDeCobertura.barrio =: barrio", Tecnico.class)
                .setParameter("barrio", barrio)
                .getResultList();
    }

}
