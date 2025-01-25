package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class TecnicoService implements WithSimplePersistenceUnit {

  private final TecnicoRepository tecnicoRepository;

  public TecnicoService(TecnicoRepository tecnicoRepository) {
    this.tecnicoRepository = tecnicoRepository;
  }

  public List<Tecnico> buscarTodos() {
    return this.tecnicoRepository.buscarTodos();
  }

  public Optional<Tecnico> buscarTecnicoPorCuit(String cuit) {
    if (cuit == null || cuit.isEmpty()) {
      throw new IllegalArgumentException("El CUIT por un Tecnico no puede ser null o vacío");
    }
    return this.tecnicoRepository.obtenerPorCuit(cuit);
  }

  public void guardarTecnico(Tecnico tecnico) {
    // TODO - validaciones
    withTransaction(() -> this.tecnicoRepository.guardar(tecnico));
  }

  public Optional<Tecnico> obtenerTecnicoPorUsuario(Usuario usuario) {
    return tecnicoRepository.buscarPorUsuario(usuario);
  }

  public void actualizar(Tecnico tecnico) {
    withTransaction(() -> tecnicoRepository.actualizar(tecnico));
  }

}
