package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.AperturaHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class AperturaHeladeraRepository implements WithSimplePersistenceUnit {
    public void agregar(AperturaHeladera apertura) {
        withTransaction(() -> entityManager().persist(apertura));
    }

    public List<AperturaHeladera> obtenerPorColaborador(Colaborador unColaborador) {
        return entityManager()
                .createQuery("from AperturaHeladera a where a = :colaborador", AperturaHeladera.class)
                .setParameter("colaborador", unColaborador)
                .getResultList();
    }
}
