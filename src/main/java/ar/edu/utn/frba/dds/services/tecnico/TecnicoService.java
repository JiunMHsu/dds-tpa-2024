package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.dtos.tecnico.CreateTecnicoDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.TecnicoDTO;
import ar.edu.utn.frba.dds.dtos.usuario.CreateUsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.IUsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Servicio de Tecnico.
 */
public class TecnicoService implements WithSimplePersistenceUnit {

  private final TecnicoRepository tecnicoRepository;
  private final IUsuarioRepository usuarioRepository;
  private final ContactoRepository contactoRepository;

  /**
   * Constructor de TecnicoService.
   *
   * @param tecnicoRepository  Repositorio de Tecnico
   * @param usuarioRepository  Repositorio de Usuario
   * @param contactoRepository Repositorio de Contacto
   */
  public TecnicoService(TecnicoRepository tecnicoRepository,
                        IUsuarioRepository usuarioRepository,
                        ContactoRepository contactoRepository) {
    this.tecnicoRepository = tecnicoRepository;
    this.usuarioRepository = usuarioRepository;
    this.contactoRepository = contactoRepository;
  }

  /**
   * Busca todos los Técnicos.
   *
   * @return Lista de TecnicoDTO
   */
  public List<TecnicoDTO> buscarTodos() {
    return this.tecnicoRepository.buscarTodos().stream()
        .map(TecnicoDTO::preview)
        .toList();
  }

  /**
   * Busca un Técnico por su ID.
   *
   * @param id ID del Técnico
   * @return TecnicoDTO
   * @throws ResourceNotFoundException Excepción de recurso no encontrado
   */
  public TecnicoDTO buscarTecnicoPorId(@NotNull String id)
      throws IllegalArgumentException {

    Tecnico tecnico = this.tecnicoRepository.buscarPorId(id)
        .orElseThrow(ResourceNotFoundException::new);

    return TecnicoDTO.completa(tecnico);
  }

  /**
   * Busca un Técnico por su Usuario.
   *
   * @param usuario Usuario del Técnico
   * @return Optional de Técnico
   */
  public Optional<Tecnico> obtenerTecnicoPorUsuario(Usuario usuario) {
    return tecnicoRepository.buscarPorUsuario(usuario);
  }

  /**
   * Busca los Técnico cuya Área de cobertura corresponda a un Barrio.
   *
   * @param barrio Barrio
   * @return Lista de Técnicos
   */
  public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
    return tecnicoRepository.obtenerPorBarrio(barrio);
  }

  /**
   * Registra un nuevo Técnico.
   *
   * @param nuevoUsuario DTO de Usuario
   * @param nuevoTecnico DTO de Técnico
   */
  public void registrarNuevoTecnico(CreateUsuarioDTO nuevoUsuario,
                                    CreateTecnicoDTO nuevoTecnico) {

    TipoRol rol = TipoRol.valueOf(nuevoUsuario.getRol().toUpperCase());

    Usuario usuario = Usuario.con(
        nuevoUsuario.getNombre(),
        nuevoUsuario.getContrasenia(),
        nuevoUsuario.getEmail(),
        rol
    );

    Documento documento = Documento.con(
        TipoDocumento.valueOf(nuevoTecnico.getTipoDocumento().toUpperCase()),
        nuevoTecnico.getNroDocumento()
    );

    Area area = new Area(
        new Ubicacion(nuevoTecnico.getLatitud(), nuevoTecnico.getLongitud()),
        nuevoTecnico.getRadio(),
        new Barrio(nuevoTecnico.getBarrio())
    );

    Contacto contacto = Contacto.conEmail(usuario.getEmail());

    final Tecnico tecnico = Tecnico.con(
        usuario,
        nuevoTecnico.getNombre(),
        nuevoTecnico.getApellido(),
        documento,
        nuevoTecnico.getCuil(),
        contacto,
        area
    );

    beginTransaction();
    this.usuarioRepository.guardar(usuario);
    this.contactoRepository.guardar(contacto);
    this.tecnicoRepository.guardar(tecnico);
    commitTransaction();
  }
}
