package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.dtos.tecnico.CreateTecnicoDTO;
import ar.edu.utn.frba.dds.dtos.usuario.CreateUsuarioDTO;
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

public class TecnicoService implements WithSimplePersistenceUnit {

  private final TecnicoRepository tecnicoRepository;
  private final IUsuarioRepository usuarioRepository;
  private final ContactoRepository contactoRepository;

  public TecnicoService(TecnicoRepository tecnicoRepository,
                        IUsuarioRepository usuarioRepository,
                        ContactoRepository contactoRepository) {
    this.tecnicoRepository = tecnicoRepository;
    this.usuarioRepository = usuarioRepository;
    this.contactoRepository = contactoRepository;
  }

  public List<Tecnico> buscarTodos() {
    return this.tecnicoRepository.buscarTodos();
  }

  public Optional<Tecnico> buscarTecnicoPorCuit(String cuit) {
    if (cuit == null || cuit.isEmpty()) {
      throw new IllegalArgumentException("El CUIT de un Tecnico no puede ser null o vacío");
    }
    return this.tecnicoRepository.obtenerPorCuit(cuit);
  }

  public Optional<Tecnico> buscarTecnicoPorId(String id) {
    if (id == null) {
      throw new IllegalArgumentException("El ID de un Tecnico no puede ser null");
    }
    return this.tecnicoRepository.obtenerPorId(id);
  }

  public Optional<Tecnico> obtenerTecnicoPorUsuario(Usuario usuario) {
    return tecnicoRepository.buscarPorUsuario(usuario);
  }

  public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
    return tecnicoRepository.obtenerPorBarrio(barrio);
  }

  public void actualizar(Tecnico tecnico) {
    withTransaction(() -> tecnicoRepository.actualizar(tecnico));
  }

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
