package ar.edu.utn.frba.dds.services.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.IUsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class ColaboradorService implements WithSimplePersistenceUnit {

  private final IColaboradorRepository colaboradorRepository;
  private final IUsuarioRepository usuarioRepository;

  public ColaboradorService(IColaboradorRepository colaboradorRepository,
                            IUsuarioRepository usuarioRepository) {
    this.colaboradorRepository = colaboradorRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public void guardar(Colaborador colaborador) {

    Usuario usuarioNuevo = colaborador.getUsuario();

    beginTransaction();
    this.usuarioRepository.guardar(usuarioNuevo);
    this.colaboradorRepository.guardar(colaborador);
    commitTransaction();
  }

  public void actualizar(Colaborador colaborador) {
    withTransaction(() -> this.colaboradorRepository.actualizar(colaborador));
  }

  public void eliminar(Colaborador colaborador) {
    this.colaboradorRepository.eliminar(colaborador);
  }

  public Optional<Colaborador> obtenerColaboradorPorUsuario(Usuario usuario) {
    if (usuario == null) {
      throw new IllegalArgumentException("El colaboradores debe tener un Usuario");
    }
    return this.colaboradorRepository.buscarPorUsuario(usuario);
  }

  public Optional<Colaborador> buscarPorId(String id) {
    return this.colaboradorRepository.buscarPorId(id);
  }

  public void eliminarColaborador(Colaborador colaborador) {
    this.colaboradorRepository.eliminar(colaborador);
  }

  public List<Colaborador> buscarTodosColaboradores() {
    return this.colaboradorRepository.buscarTodos();
  }

  public Optional<Colaborador> obtenerColaboradorPorID(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("El ID del colaborador no puede ser null o vacío");
    }

    return this.colaboradorRepository.buscarPorId(id);
  }

  /**
   * Registra chatID correspondiente
   *
   * @param colaborador
   * @param chatId
   */
  public void registrarChatId(@NotNull Colaborador colaborador, String chatId) {

    colaborador.agregarContacto(Contacto.conTelegram(chatId));
    this.colaboradorRepository.guardar(colaborador);
  }
}
