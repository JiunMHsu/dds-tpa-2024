package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.rol.Rol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.usuario.RolRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init() {
        Initializer instance = new Initializer();

        instance.cleanupDatabase();
        instance.withSuperUser();
    }

    public void withSuperUser() {
        Rol admin = new Rol("ADMIN", new ArrayList<>());
        Usuario superUser = Usuario.con(
                "Admin del Sistema",
                "0000",
                "utn.dds.g22@gmail.com",
                admin
        );

        RolRepository rolRepositoryepo = new RolRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        withTransaction(() -> {
            rolRepositoryepo.guardar(admin);
            usuarioRepository.guardar(superUser);
        });
    }

    private void cleanupDatabase() {
        withTransaction(() -> {
        });
    }
}
