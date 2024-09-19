package ar.edu.utn.frba.dds.repository.tarjeta;

//import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class TarjetaColaboradorRepository implements WithSimplePersistenceUnit {
    public void guardar(TarjetaColaborador tarjeta) {
        withTransaction(() -> entityManager().persist(tarjeta));
    }

    public TarjetaColaborador obtenerPorCodigo(String codigo) {
        return entityManager()
                .createQuery("from TarjetaColaborador t where t.codigo =: codigo", TarjetaColaborador.class)
                .setParameter("codigo", codigo)
                .getSingleResult();
    }
}
