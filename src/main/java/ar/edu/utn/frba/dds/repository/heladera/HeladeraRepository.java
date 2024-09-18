package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class HeladeraRepository implements WithSimplePersistenceUnit {
    public static void agregar(Heladera heladera) {
    }

    public static List<Heladera> obtenerTodos() {
        return null;
    }

    private static Heladera buscarPor(String nombre) {
        return null;
    }

    public List<Heladera> obtenerPorBarrio(Barrio barrio) {
        return entityManager()
                .createQuery("from Heladera h where h.direccion.barrio =: barrio", Heladera.class)
                .setParameter("barrio", barrio)
                .getResultList();
    }

}
