package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init() {
        Initializer instance = new Initializer();

        instance.cleanupDatabase();
        instance.withSuperUser();
    }

    public void withSuperUser() {
        Usuario superUser = Usuario.con(
                "Admin del Sistema",
                "0000",
                "utn.dds.g22@gmail.com",
                TipoRol.ADMIN
        );

        UsuarioRepository usuarioRepository = new UsuarioRepository();

        withTransaction(() -> {
            usuarioRepository.guardar(superUser);
        });
    }

    private void cleanupDatabase() {
        withTransaction(() -> {
        });
    }
}
