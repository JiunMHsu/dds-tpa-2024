package ar.edu.utn.frba.dds.services.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class ColaboradorService implements WithSimplePersistenceUnit {

    private final IColaboradorRepository colaboradorRepository;

    public ColaboradorService(IColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public Optional<Colaborador> buscarPorId(String id) {
        return this.colaboradorRepository.buscarPorId(id);
    }

    public void guardarColaborador(Colaborador colaborador) {
        // TODO - validaciones
        withTransaction(() -> this.colaboradorRepository.guardar(colaborador));
    }

    public void actualizarColaborador(Colaborador colaboradorActualizado) {
        this.colaboradorRepository.actualizar(colaboradorActualizado);
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

    public void guardar(Colaborador colaborador) {
        // TODO - validaciones??
        withTransaction(() -> this.colaboradorRepository.guardar(colaborador));
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


}
