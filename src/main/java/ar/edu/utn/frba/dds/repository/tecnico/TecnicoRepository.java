package ar.edu.utn.frba.dds.repository.tecnico;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import lombok.Getter;

@Getter
public class TecnicoRepository implements WithSimplePersistenceUnit {
    public void agregar(Tecnico tecnico) {
        entityManager().persist(tecnico);
    }

    public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
        return entityManager()
                .createQuery("from Tecnico t where t.areaDeCobertura.barrio =: barrio", Tecnico.class)
                .setParameter("barrio", barrio)
                .getResultList();
    }

}
