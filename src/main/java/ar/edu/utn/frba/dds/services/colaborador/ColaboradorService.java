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

/**
 * Servicio de Colaborador.
 */
public class ColaboradorService implements WithSimplePersistenceUnit {

  private final IColaboradorRepository colaboradorRepository;
  private final IUsuarioRepository usuarioRepository;

  /**
   * Constructor de ColaboradorService.
   *
   * @param colaboradorRepository Repositorio de Colaborador
   * @param usuarioRepository     Repositorio de Usuario
   */
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

  public List<Colaborador> buscarTodosColaboradores() {
    return this.colaboradorRepository.buscarTodos();
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
}
