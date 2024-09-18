package ar.edu.utn.frba.dds.repository.mensajeria;

import ar.edu.utn.frba.dds.mensajeria.Mensaje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;

@Getter
public class MensajeRepository implements WithSimplePersistenceUnit {

    public void agregar(Mensaje mensaje) {
        entityManager().persist(mensaje);
    }

//  public Mensaje obtenerUltimo() {
//    return entityManager()
//            .createQuery("from Mensaje m order by m.fechaEnvio desc", Mensaje.class)
//            .setMaxResults(1)
//            .getSingleResult();
//  }

//  public void limpiar() {
//    entityManager().createQuery("DELETE FROM Mensaje").executeUpdate();
//  }
}
