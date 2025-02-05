package ar.edu.utn.frba.dds.services.colaborador;

import ar.edu.utn.frba.dds.dtos.colaborador.ColaboradorDTO;
import ar.edu.utn.frba.dds.dtos.colaborador.CreateColaboradorDTO;
import ar.edu.utn.frba.dds.dtos.usuario.CreateUsuarioDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.colaborador.TipoColaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.IUsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Servicio de Colaborador.
 */
public class ColaboradorService implements WithSimplePersistenceUnit {

  private final IColaboradorRepository colaboradorRepository;
  private final IUsuarioRepository usuarioRepository;
  private final ContactoRepository contactoRepository;

  /**
   * Constructor de ColaboradorService.
   *
   * @param colaboradorRepository Repositorio de Colaborador
   * @param usuarioRepository     Repositorio de Usuario
   * @param contactoRepository    Repositorio de Contacto
   */
  public ColaboradorService(IColaboradorRepository colaboradorRepository,
                            IUsuarioRepository usuarioRepository,
                            ContactoRepository contactoRepository) {
    this.colaboradorRepository = colaboradorRepository;
    this.usuarioRepository = usuarioRepository;
    this.contactoRepository = contactoRepository;
  }

  /**
   * Actualiza las formas de colaborar de un Colaborador.
   *
   * @param colaborador    Colaborador a actualizar
   * @param colaboraciones Nueva lista de colaboraciones
   */
  public void actualizarFormasColaborar(Colaborador colaborador, List<String> colaboraciones) {

    ArrayList<TipoColaboracion> nuevasColaboraciones = colaboraciones.stream()
        .map(TipoColaboracion::valueOf).collect(Collectors.toCollection(ArrayList::new));

    colaborador.setFormasDeColaborar(nuevasColaboraciones);

    withTransaction(() -> this.colaboradorRepository.actualizar(colaborador));
  }

  /**
   * Obtiene un Colaborador por Usuario.
   *
   * @param usuario Usuario
   * @return Optional de Colaborador
   */
  public Optional<Colaborador> obtenerColaboradorPorUsuario(Usuario usuario) {
    if (usuario == null) {
      throw new IllegalArgumentException("El colaboradores debe tener un Usuario");
    }
    return this.colaboradorRepository.buscarPorUsuario(usuario);
  }

  /**
   * Busca un Colaborador por Id.
   *
   * @param id Id del Colaborador
   * @return Optional de Colaborador
   */
  public Optional<Colaborador> buscarPorId(String id) {
    return this.colaboradorRepository.buscarPorId(id);
  }

  /**
   * Busca todos los Colaboradores.
   *
   * @return Lista de ColaboradorDTO
   */
  public List<ColaboradorDTO> buscarTodosColaboradores() {

    List<Colaborador> todosColaboradores = this.colaboradorRepository.buscarTodos();
    List<ColaboradorDTO> colaboradores = todosColaboradores.stream()
        .map(ColaboradorDTO::preview)
        .toList();

    return colaboradores;
  }

  /**
   * Registrar un chatId a un Colaborador.
   *
   * @param colaborador colaborador
   * @param chatId      chatId a registrar
   */
  public void registrarChatId(@NotNull Colaborador colaborador, @NotNull String chatId) {

    colaborador.agregarContacto(Contacto.conTelegram(chatId));
    this.colaboradorRepository.guardar(colaborador);
  }

  /**
   * Registrar un nuevo Colaborador.
   *
   * @param nuevoColaborador DTO del nuevo Colaborador
   */
  public void registrarNuevoColaborador(CreateUsuarioDTO nuevoUsuario,
                                        CreateColaboradorDTO nuevoColaborador) {

    Direccion direccion = Direccion.con(
        new Barrio(nuevoColaborador.getBarrio()),
        new Calle(nuevoColaborador.getCalle()),
        Integer.parseInt(nuevoColaborador.getAltura())
    );

    TipoRol rol = TipoRol.valueOf(nuevoUsuario.getRol().toUpperCase());

    Usuario usuario = Usuario.con(
        nuevoUsuario.getNombre(),
        nuevoUsuario.getContrasenia(),
        nuevoUsuario.getEmail(),
        rol
    );

    TipoColaborador tipo = TipoColaborador
        .valueOf(nuevoColaborador.getTipo().toUpperCase());

    TipoRazonSocial tipoRazonSocial = null;
    if (tipo.esJuridico()) {
      tipoRazonSocial = TipoRazonSocial
          .valueOf(nuevoColaborador.getTipoRazonSocial().trim().toUpperCase());
    }

    LocalDate fechaNacimiento = null;
    if (tipo.esHumano()) {
      fechaNacimiento = LocalDate.parse(nuevoColaborador.getFechaNacimiento());
    }

    List<Contacto> contactos = List.of(
        Contacto.conEmail(nuevoUsuario.getEmail()),
        Contacto.conTelefono(nuevoColaborador.getTelefono()),
        Contacto.conWhatsApp("whatsapp:" + nuevoColaborador.getWhatsapp())
    );

    List<TipoColaboracion> formasDeColaborar = Arrays
        .stream(nuevoColaborador.getFormaDeColaborar().split(","))
        .map(String::trim)
        .map(TipoColaboracion::valueOf)
        .collect(Collectors.toList());

    final Colaborador colaborador = Colaborador.builder()
        .tipoColaborador(tipo)
        .usuario(usuario)
        .direccion(direccion)
        .contactos(contactos)
        .formasDeColaborar(formasDeColaborar)
        .nombre(nuevoColaborador.getNombre())
        .apellido(nuevoColaborador.getApellido())
        .razonSocial(nuevoColaborador.getRazonSocial())
        .tipoRazonSocial(tipoRazonSocial)
        .rubro(nuevoColaborador.getRubro())
        .fechaNacimiento(fechaNacimiento)
        .build();

    beginTransaction();
    this.usuarioRepository.guardar(usuario);
    this.contactoRepository.guardar(contactos);
    this.colaboradorRepository.guardar(colaborador);
    commitTransaction();
  }
}
