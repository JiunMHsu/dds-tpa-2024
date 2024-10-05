package ar.edu.utn.frba.dds.models.repositories.usuario;

import ar.edu.utn.frba.dds.models.entities.rol.Rol;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RolRepository implements WithSimplePersistenceUnit {

    public void guardar(Rol rol) {
        entityManager().persist(rol);
    }

    public void actualizar(Rol rol) {
        withTransaction(() -> {
            entityManager().merge(rol);
        });
    }

    public void eliminar(Rol rol) {
        withTransaction(() -> {
            rol.setAlta(false);
            entityManager().merge(rol);
        });
    }
}
