package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PruebaDB implements WithSimplePersistenceUnit {

    private HeladeraRepository heladeraRepository;
    private TecnicoRepository tecnicoRepository;
    private ColaboradorRepository colaboradorRepository;
    private UsuarioRepository usuarioRepository;

    public static void main(String[] args) {
        PruebaDB instance = new PruebaDB();

        instance.usuarioRepository = new UsuarioRepository();

        instance.heladeraRepository = new HeladeraRepository();
        instance.guardarHeladera();
        instance.recuperarHeladera();

        instance.tecnicoRepository = new TecnicoRepository();
        instance.guardarTecnico();
        instance.recuperarTecnico();

        instance.colaboradorRepository = new ColaboradorRepository();
        instance.guardarColaborador();
        instance.recuperarColaborador();

    }

    private void guardarHeladera() {
        Direccion direccion = new Direccion(
                new Barrio("Almagro"),
                new Calle("Medrano"),
                951,
                new Ubicacion(-34.59857981526152, -58.420110294464294)
        );

        Heladera heladera = Heladera.con(
                "UTN Medrano",
                direccion,
                20,
                new RangoTemperatura(5.0, -10.0)
        );

        heladera.setEstado(EstadoHeladera.INACTIVA);
        heladera.setViandas(0);

        heladeraRepository.guardar(heladera);
    }

    private void recuperarHeladera() {
        Optional<Heladera> heladeraRecuperada = heladeraRepository.buscarPorNombre("UTN Medrano");

        heladeraRecuperada.ifPresent(heladera ->
                System.out.println("Heladera: " + heladera.getNombre() + "\n" + "Id: " + heladera.getId().toString())
        );
    }

    private void guardarColaborador() {
        Usuario unUsuario = Usuario.con("JiunMHsu", "iMC4(*&A^F0OK?%87", "utn.dds.g22@gmail.com", TipoRol.ADMIN);
        Direccion direccion = new Direccion(
                new Barrio("Almagro"),
                new Calle("Medrano"),
                951,
                new Ubicacion(-34.59857981526152, -58.420110294464294)
        );

        Colaborador unColaborador = Colaborador.humana(
                unUsuario,
                "Jiun Ming",
                "Hsu",
                LocalDate.of(2003, 2, 19)
        );

        unColaborador.setDireccion(direccion);

        withTransaction(() -> {
            usuarioRepository.guardar(unUsuario);
            colaboradorRepository.guardar(unColaborador);
        });
    }

    private void recuperarColaborador() {
        Optional<Colaborador> unColaborador = colaboradorRepository.buscarPorEmail("hsujm219@gmail.com");

        unColaborador.ifPresent(colaborador -> System.out.println(
                "Colaborador: " + colaborador.getNombre() + "\n"
                        + "Id: " + colaborador.getId().toString() + "\n"
                        + "Usuario: " + colaborador.getUsuario().getNombre() + "\n"
                        + "Clave: " + colaborador.getUsuario().getContrasenia() + "\n"
        ));
    }

    private void guardarTecnico() {
        Barrio almagro = new Barrio("Almagro");

        Tecnico unTecnico = Tecnico.con(
                "Matias Leonel",
                "Juncos Mieres",
                new Documento(TipoDocumento.DNI, "12345678"),
                "24-12345678-0",
                Contacto.conTelegram("7652931546"),
                MedioDeNotificacion.TELEGRAM,
                new Area(new Ubicacion(-34.60011743355092, -58.417371449916324), 500.0, almagro)
        );

        Tecnico otroTecnico = Tecnico.con(
                "Joaquín",
                "Gándola",
                new Documento(TipoDocumento.DNI, "82738291"),
                "22-82738291-1",
                Contacto.conWhatsApp("8881928172"),
                MedioDeNotificacion.WHATSAPP,
                new Area(new Ubicacion(-34.60711989660622, -58.414045825102896), 400.0, almagro)
        );

        tecnicoRepository.guardar(unTecnico);
        tecnicoRepository.guardar(otroTecnico);
    }

    private void recuperarTecnico() {
        List<Tecnico> tecnicosDeAlmagro = tecnicoRepository.obtenerPorBarrio(new Barrio("Almagro"));

        if (!tecnicosDeAlmagro.isEmpty()) {
            for (Tecnico tecnico : tecnicosDeAlmagro) {
                System.out.println("Tecnico: " + tecnico.getNombre() + "\n"
                        + "Id: " + tecnico.getId().toString() + "\n"
                        + "Fecha Alta: " + tecnico.getFechaAlta() + "\n");
            }
        }
    }

    public void impactarEnBase() {
        withTransaction(() -> {
        });
    }
}
