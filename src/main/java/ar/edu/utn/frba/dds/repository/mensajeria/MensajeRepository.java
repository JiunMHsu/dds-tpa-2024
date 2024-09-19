package ar.edu.utn.frba.dds.repository.mensajeria;

import ar.edu.utn.frba.dds.mensajeria.Mensaje;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
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

    //  public Mensaje obtenerUltimo() {
    //    return entityManager()
    //            .createQuery("from Mensaje m order por m.fechaEnvio desc", Mensaje.class)
    //            .setMaxResults(1)
    //            .getSingleResult();
    //  }

}
