package ar.edu.utn.frba.dds.repository.incidente;

import ar.edu.utn.frba.dds.models.incidente.Alerta;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class AlertaRepository implements WithSimplePersistenceUnit {
    public void guardar(Alerta alerta) {
        withTransaction(() -> entityManager().persist(alerta));
    }

    public List<Alerta> obtenerPorTipo(TipoIncidente tipoIncidente) {
        return entityManager()
                .createQuery("from Alerta a where a.tipoIncidente = :tipo_incidente", Alerta.class)
                .setParameter("tipo_incidente", tipoIncidente)
                .getResultList();
    }

}
