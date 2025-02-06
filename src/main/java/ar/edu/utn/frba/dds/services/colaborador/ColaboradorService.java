package ar.edu.utn.frba.dds.services.colaborador;

import ar.edu.utn.frba.dds.dtos.colaborador.ColaboradorDTO;
import ar.edu.utn.frba.dds.dtos.colaborador.CreateColaboradorDTO;
import ar.edu.utn.frba.dds.dtos.usuario.CreateUsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
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
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaColaboradorService;
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
  private final TarjetaColaboradorService tarjetaColaboradorService;

  /**
   * Constructor de ColaboradorService.
   *
   * @param colaboradorRepository Repositorio de Colaborador
   * @param usuarioRepository     Repositorio de Usuario
   * @param contactoRepository    Repositorio de Contacto
   */
  public ColaboradorService(IColaboradorRepository colaboradorRepository,
                            IUsuarioRepository usuarioRepository,
                            ContactoRepository contactoRepository,
                            TarjetaColaboradorService tarjetaColaboradorService) {
    this.colaboradorRepository = colaboradorRepository;
    this.usuarioRepository = usuarioRepository;
    this.contactoRepository = contactoRepository;
    this.tarjetaColaboradorService = tarjetaColaboradorService;
  }

  /**
   * Actualiza las formas de colaborar de un Colaborador.
   *
   * @param colaborador    Colaborador a actualizar
   * @param colaboraciones Nueva lista de colaboraciones
   */
  public void actualizarFormasColaborar(Colaborador colaborador, List<String> colaboraciones) {

    List<TipoColaboracion> nuevasColaboraciones = colaboraciones.stream()
        .map(String::toUpperCase)
        .map(TipoColaboracion::valueOf)
        .toList();

    boolean puedeColaborar =
        new ArrayList<>(colaborador.getTipoColaborador().colaboracionesPermitidas())
            .containsAll(nuevasColaboraciones);

    if (!puedeColaborar) {
      throw new InvalidFormParamException();
    }

    colaborador.setFormasDeColaborar(nuevasColaboraciones);

    beginTransaction();
    this.colaboradorRepository.actualizar(colaborador);

    if (deberiaTenerTarjeta(colaborador)) {
      this.tarjetaColaboradorService.generarTarjetaPara(colaborador);
    }
    // ¿Dar de baja en caso contrario??
    commitTransaction();
  }

  /**
   * Obtiene un Colaborador por Usuario.
   *
   * @param usuario Usuario
   * @return Optional de Colaborador
   */
  public Optional<Colaborador> obtenerColaboradorPorUsuario(@NotNull Usuario usuario) {
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
    return this.colaboradorRepository.buscarTodos().stream()
        .map(ColaboradorDTO::preview)
        .toList();
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

    final Direccion direccion = Direccion.con(
        new Barrio(nuevoColaborador.getBarrio()),
        new Calle(nuevoColaborador.getCalle()),
        Integer.parseInt(nuevoColaborador.getAltura())
    );

    TipoRol rol = TipoRol.valueOf(nuevoUsuario.getRol().toUpperCase());

    final Usuario usuario = Usuario.con(
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

    List<Contacto> contactos = new ArrayList<>();

    contactos.add(Contacto.conEmail(nuevoUsuario.getEmail()));

    if (!nuevoColaborador.getTelefono().isEmpty()) {
      contactos.add(Contacto.conTelefono(nuevoColaborador.getTelefono()));
    }

    if (!nuevoColaborador.getWhatsapp().isEmpty()) {
      Contacto.conWhatsApp("whatsapp:" + nuevoColaborador.getWhatsapp());
    }

    List<TipoColaboracion> formasDeColaborar = Arrays
        .stream(nuevoColaborador.getFormaDeColaborar().split(","))
        .map(String::trim)
        .map(TipoColaboracion::valueOf)
        .collect(Collectors.toList());

    boolean puedeColaborar =
        new ArrayList<>(tipo.colaboracionesPermitidas()).containsAll(formasDeColaborar);

    if (!puedeColaborar) {
      throw new InvalidFormParamException();
    }

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

    if (deberiaTenerTarjeta(colaborador)) {
      this.tarjetaColaboradorService.generarTarjetaPara(colaborador);
    }
    commitTransaction();
  }

  private boolean deberiaTenerTarjeta(Colaborador colaborador) {
    List<TipoColaboracion> colaboraciones = colaborador.getFormasDeColaborar();

    return colaboraciones.contains(TipoColaboracion.DISTRIBUCION_VIANDAS)
        || colaboraciones.contains(TipoColaboracion.DONACION_VIANDAS);
  }
}
