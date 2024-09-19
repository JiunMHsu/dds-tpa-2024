package ar.edu.utn.frba.dds.repository.tarjeta;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class TarjetaPersonaVulnerableRepository implements WithSimplePersistenceUnit {

    public void guardar(TarjetaPersonaVulnerable tarjeta) {
        withTransaction(() -> entityManager().persist(tarjeta));    }
    public TarjetaPersonaVulnerable obtenerPorCodigo(String codigo) {
        return entityManager()
                .createQuery("from TarjetaPersonaVulnerable t where t.codigo =: codigo", TarjetaPersonaVulnerable.class)
                .setParameter("codigo", codigo)
                .getSingleResult();
    }
}
