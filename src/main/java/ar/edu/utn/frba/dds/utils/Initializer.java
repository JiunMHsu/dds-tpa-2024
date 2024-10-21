package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init() {
        Initializer instance = new Initializer();

        instance.cleanupDatabase();
        instance.withSuperUser();
        instance.withColaborador();
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

    public void withColaborador() {
        Usuario usuario = Usuario.con("JiunMHsu", "iMC4(*&A^F0OK?%87", "jhsu@gmail.com", TipoRol.COLABORADOR);

        Direccion direccion = new Direccion(
                new Barrio("Almagro"),
                new Calle("Medrano"),
                951,
                new Ubicacion(-34.59857981526152, -58.420110294464294)
        );

        List<Colaboracion> colaboraciones = new ArrayList<>();
        colaboraciones.add(Colaboracion.DISTRIBUCION_VIANDAS);
        colaboraciones.add(Colaboracion.DONACION_DINERO);

        Colaborador colaborador = Colaborador.humana(
                usuario,
                "Jiun Ming",
                "Hsu",
                LocalDate.of(2003, 2, 19),
                Contacto.vacio(),
                direccion,
                colaboraciones);

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ColaboradorRepository colaboradorRepository = new ColaboradorRepository();

        withTransaction(() -> {
            usuarioRepository.guardar(usuario);
            colaboradorRepository.guardar(colaborador);
        });
    }

    private void cleanupDatabase() {
        withTransaction(() -> {
        });
    }
}
