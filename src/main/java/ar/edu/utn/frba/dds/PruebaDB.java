package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import ar.edu.utn.frba.dds.repository.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import ar.edu.utn.frba.dds.repository.usuario.UsuarioRepository;
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
        Optional<Heladera> heladeraRecuperada = heladeraRepository.obtenerPorNombre("UTN Medrano");

        heladeraRecuperada.ifPresent(heladera ->
                System.out.println("Heladera: " + heladera.getNombre() + "\n" + "Id: " + heladera.getId().toString())
        );
    }

    private void guardarColaborador() {
        Usuario unUsuario = Usuario.with("JiunMHsu", "iMC4(*&A^F0OK?%87", "hsujm219@gmail.com");
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
                        + "Id: " + colaborador.getId().toString()
                        + "Usuario: " + colaborador.getUsuario().getNombre() + "\n"
                        + "Clave: " + colaborador.getUsuario().getContrasenia() + "\n"
        ));
    }

    private void guardarTecnico() {
        Barrio almagro = new Barrio("Almagro");

        Tecnico unTecnico = Tecnico.con(
                "Matias Leonel",
                "Juncos Mieres",
                new Documento("12345678", TipoDocumento.DNI),
                "24-12345678-0",
                Contacto.conTelegram("7652931546"),
                MedioDeNotificacion.TELEGRAM,
                new Area(new Ubicacion(-34.60011743355092, -58.417371449916324), 500.0, almagro)
        );

        Tecnico otroTecnico = Tecnico.con(
                "Joaquín",
                "Gándola",
                new Documento("82738291", TipoDocumento.DNI),
                "22-82738291-1",
                Contacto.conWhatsApp("8881928172"),
                MedioDeNotificacion.WHATSAPP,
                new Area(new Ubicacion(-34.60711989660622, -58.414045825102896), 400.0, almagro)
        );

        withTransaction(() -> {
            tecnicoRepository.guardar(unTecnico);
            tecnicoRepository.guardar(otroTecnico);
        });
    }

    private void recuperarTecnico() {
        List<Tecnico> tecnicosDeAlmagro = tecnicoRepository.obtenerPorBarrio(new Barrio("Almagro"));

        if (!tecnicosDeAlmagro.isEmpty()) {
            for (Tecnico tecnico : tecnicosDeAlmagro) {
                System.out.println("Tecnico: " + tecnico.getNombre());
            }
        }
    }

    public void impactarEnBase() {
        withTransaction(() -> {
        });
    }
}
