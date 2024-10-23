package ar.edu.utn.frba.dds.models.repositories.vianda;

import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class ViandaRepository implements WithSimplePersistenceUnit {

    public void guardar(Vianda vianda) {
        withTransaction(() -> entityManager().persist(vianda));
    }
}
