package ar.edu.utn.frba.dds.models.repositories.mensajeria;

import ar.edu.utn.frba.dds.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class MensajeRepository implements WithSimplePersistenceUnit {

    public void agregar(Mensaje mensaje) {
        entityManager().persist(mensaje);
    }

    public List<Mensaje> obtenerPorColaborador(Colaborador unColaborador) {
        return entityManager()
                .createQuery("from Mensaje m where m.receptor = :colaborador", Mensaje.class)
                .setParameter("colaborador", unColaborador)
                .getResultList();
    }
}
