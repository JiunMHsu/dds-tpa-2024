package ar.edu.utn.frba.dds.repository.incidente;

import ar.edu.utn.frba.dds.models.incidente.FallaTecnica;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class FallaTecnicaRepository implements WithSimplePersistenceUnit {

    public void guardar(FallaTecnica fallaTecnica) {
        withTransaction(() -> entityManager().persist(fallaTecnica));
    }

}
