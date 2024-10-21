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

    public List<Colaborador> buscarTodos() {
        return colaboradorRepository.buscarTodos();
    }

    public Optional<Colaborador> buscarPorId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del colaborador no puede ser null o vacío");
        }

        return colaboradorRepository.buscarPorId(id);
    }

    public void guardar(Colaborador colaborador) {
        // TODO - validaciones??
        withTransaction(() -> colaboradorRepository.guardar(colaborador));
    }

    public void actualizar(Colaborador colaborador) {
        withTransaction(() -> colaboradorRepository.actualizar(colaborador));
    }

    public void eliminar(Colaborador colaborador) {
        colaboradorRepository.eliminar(colaborador);
    }

    public Optional<Colaborador> obtenerColaboradorPorUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El colaboradores debe tener un Usuario");
        }
        return colaboradorRepository.buscarPorUsuario(usuario);
    }


}
