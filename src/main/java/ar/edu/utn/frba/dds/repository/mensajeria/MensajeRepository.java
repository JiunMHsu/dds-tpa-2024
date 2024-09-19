package ar.edu.utn.frba.dds.repository.mensajeria;

import ar.edu.utn.frba.dds.mensajeria.Mensaje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class MensajeRepository implements WithSimplePersistenceUnit {

    public void agregar(Mensaje mensaje) {
        entityManager().persist(mensaje);
    }


    // TODO - Get por colaborador

    //  public Mensaje obtenerUltimo() {
    //    return entityManager()
    //            .createQuery("from Mensaje m order por m.fechaEnvio desc", Mensaje.class)
    //            .setMaxResults(1)
    //            .getSingleResult();
    //  }

}
