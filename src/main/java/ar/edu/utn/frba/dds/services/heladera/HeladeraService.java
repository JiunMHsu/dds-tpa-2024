package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class HeladeraService implements WithSimplePersistenceUnit {

    private final HeladeraRepository heladeraRepository;

    public HeladeraService(HeladeraRepository heladeraRepository) {
        this.heladeraRepository = heladeraRepository;
    }

    public List<Heladera> buscarTodas() {
        return this.heladeraRepository.buscarTodos();
    }

    public Optional<Heladera> buscarPorId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("El ID de la heladera no puede ser null o vacío");

        return this.heladeraRepository.buscarPorId(id);
    }

    public Optional<Heladera> buscarHeladeraPorNombre(String nombre) {
        return this.heladeraRepository.buscarPorNombre(nombre);
    }

    public void guardarHeladera(Heladera heladera) {
        // TODO - validaciones??
        withTransaction(() -> this.heladeraRepository.guardar(heladera));
    }

    // Lo dejo asi medio basico, pero seguramente se deba validar que los nuevos atributos:
    // 1. no sean null o sean coherentes (asumo)
    // 2. no sean exactamente los mismos
    public void actualizarHeladera(Heladera heladeraActualizada) {
        this.heladeraRepository.actualizar(heladeraActualizada);
    }

    public void eliminarHeladera(Heladera heladera) {
        this.heladeraRepository.eliminar(heladera);
    }
}
