package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.ArrayList;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init() {
        Initializer instance = new Initializer();

        instance.cleanupDatabase();
        instance.withSuperUser();
        instance.withColaborador();
        instance.withHeladeras();
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

        ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
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

    public void withHeladeras() {

        Ubicacion u1 = new Ubicacion(-34.6037, -58.3816);
        Ubicacion u2 = new Ubicacion(-34.6040, -58.3800);
        Ubicacion u3 = new Ubicacion(-34.6050, -58.3825);
        Ubicacion u4 = new Ubicacion(-34.6035, -58.3830);
        Ubicacion u5 = new Ubicacion(-34.6028, -58.3810);
        Ubicacion u6 = new Ubicacion(-34.6060, -58.3845);
        Ubicacion u7 = new Ubicacion(-34.6045, -58.3790);
        Ubicacion u8 = new Ubicacion(-34.6058, -58.3805);
        Ubicacion u9 = new Ubicacion(-34.6032, -58.3820);
        Ubicacion u10 = new Ubicacion(-34.6070, -58.3850);
        Ubicacion u11 = new Ubicacion(-34.6025, -58.3795);
        Ubicacion u12 = new Ubicacion(-34.6080, -58.3835);
        Ubicacion u13 = new Ubicacion(-34.6090, -58.3860);
        Ubicacion u14 = new Ubicacion(-34.6010, -58.3785);
        Ubicacion u15 = new Ubicacion(-34.6075, -58.3800);

        Barrio b1 = new Barrio("Palermo");
        Barrio b2 = new Barrio("Recoleta");
        Barrio b3 = new Barrio("San Telmo");
        Barrio b4 = new Barrio("Belgrano");
        Barrio b5 = new Barrio("La Boca");
        Barrio b6 = new Barrio("Almagro");
        Barrio b7 = new Barrio("Villa Urquiza");
        Barrio b8 = new Barrio("Caballito");
        Barrio b9 = new Barrio("Nuñez");
        Barrio b10 = new Barrio("Puerto Madero");

        Calle c1 = new Calle("Avenida Corrientes");
        Calle c2 = new Calle("Florida");
        Calle c3 = new Calle("Avenida 9 de Julio");
        Calle c4 = new Calle("Avenida Santa Fe");
        Calle c5 = new Calle("Avenida Medrano");
        Calle c6 = new Calle("Avenida de Mayo");
        Calle c7 = new Calle("Avenida Libertador");
        Calle c8 = new Calle("Lavalle");
        Calle c9 = new Calle("Avenida Pueyrredón");
        Calle c10 = new Calle("Avenida Alvear");

        Direccion d1 = new Direccion(b6, c2, 1765, u1);
        Direccion d2 = new Direccion(b7, c3, 1933, u2);
        Direccion d3 = new Direccion(b6, c5, 734, u3);
        Direccion d4 = new Direccion(b6, c2, 871, u4);
        Direccion d5 = new Direccion(b5, c9, 178, u5);
        Direccion d6 = new Direccion(b3, c1, 2330, u6);
        Direccion d7 = new Direccion(b1, c4, 1251, u7);
        Direccion d8 = new Direccion(b4, c6, 1765, u8);
        Direccion d9 = new Direccion(b2, c7, 1123, u9);
        Direccion d10 = new Direccion(b6, c8, 2915, u10);
        Direccion d11 = new Direccion(b10, c10, 3644, u11);
        Direccion d12 = new Direccion(b9, c2, 1131, u12);
        Direccion d13 = new Direccion(b8, c3, 3331, u13);
        Direccion d14 = new Direccion(b10, c4, 3242, u14);
        Direccion d15 = new Direccion(b10, c5, 3633, u15);

        HeladeraRepository heladeraRepository = new HeladeraRepository();

        beginTransaction();
        heladeraRepository.guardar(Heladera.con("Heladera DIEZ", d10, 80, new RangoTemperatura(5.0, -5.0), 75));
        heladeraRepository.guardar(Heladera.con("Heladera CINCO", d5, 60, new RangoTemperatura(5.0, -4.0), 52));
        heladeraRepository.guardar(Heladera.con("Heladera NUEVE", d9, 90, new RangoTemperatura(4.0, -4.0), 67));
        heladeraRepository.guardar(Heladera.con("Heladera UNO", d1, 80, new RangoTemperatura(3.0, -5.0), 58));
        heladeraRepository.guardar(Heladera.con("Heladera CATORCE", d14, 65, new RangoTemperatura(5.0, -5.0), 46));
        heladeraRepository.guardar(Heladera.con("Heladera ONCE", d11, 85, new RangoTemperatura(3.0, -4.0), 47));
        heladeraRepository.guardar(Heladera.con("Heladera DOCE", d12, 70, new RangoTemperatura(5.0, -3.0), 61));
        heladeraRepository.guardar(Heladera.con("Heladera QUINCE", d15, 80, new RangoTemperatura(3.0, -3.0), 80));
        heladeraRepository.guardar(Heladera.con("Heladera TRECE", d13, 95, new RangoTemperatura(2.0, -4.0), 83));
        heladeraRepository.guardar(Heladera.con("Heladera CUATRO", d4, 55, new RangoTemperatura(3.0, -4.0), 35));
        heladeraRepository.guardar(Heladera.con("Heladera OCHO", d8, 70, new RangoTemperatura(3.0, -2.0), 55));
        heladeraRepository.guardar(Heladera.con("Heladera SIETE", d7, 80, new RangoTemperatura(3.0, -4.0), 76));
        heladeraRepository.guardar(Heladera.con("Heladera DOS", d2, 70, new RangoTemperatura(2.0, -3.0), 42));
        heladeraRepository.guardar(Heladera.con("Heladera SEIS", d6, 60, new RangoTemperatura(4.0, -4.0), 44));
        heladeraRepository.guardar(Heladera.con("Heladera TRES", d3, 85, new RangoTemperatura(3.0, -4.0), 66));
        commitTransaction();
    }

    private void cleanupDatabase() {
        withTransaction(() -> {
        });
    }
}
