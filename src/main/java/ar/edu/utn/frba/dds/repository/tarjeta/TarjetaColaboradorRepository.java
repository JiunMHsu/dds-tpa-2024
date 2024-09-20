package ar.edu.utn.frba.dds.repository.tarjeta;

import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class TarjetaColaboradorRepository implements WithSimplePersistenceUnit {
    public void guardar(TarjetaColaborador tarjeta) {
        withTransaction(() -> entityManager().persist(tarjeta));
    }

    public void actualizar(TarjetaColaborador tarjeta) {
        withTransaction(() -> entityManager().merge(tarjeta));
    }

    public void eliminar(TarjetaColaborador tarjeta) {
        withTransaction(() -> {
            tarjeta.setAlta(false);
            entityManager().merge(tarjeta);
        });
    }

    public Optional<TarjetaColaborador> obtenerPorCodigo(String codigo) {
        return Optional.ofNullable(entityManager()
                .createQuery("from TarjetaColaborador t where t.codigo = :codigo and t.alta = :alta", TarjetaColaborador.class)
                .setParameter("codigo", codigo)
                .setParameter("alta", true)
                .getSingleResult());
    }
}
