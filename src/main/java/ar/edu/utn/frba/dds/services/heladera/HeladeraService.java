package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class HeladeraService implements WithSimplePersistenceUnit {

    private final HeladeraRepository heladeraRepository;
    private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

    public HeladeraService(HeladeraRepository heladeraRepository,
                           HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository) {
        this.heladeraRepository = heladeraRepository;
        this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
    }

    public List<Heladera> buscarTodas() {
        return this.heladeraRepository.buscarTodos();
    }

    public Optional<Heladera> buscarPorId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("El ID por la heladera no puede ser null o vacío");

        return this.heladeraRepository.buscarPorId(id);
    }

    public Optional<Heladera> buscarPorNombre(String nombre) {
        return this.heladeraRepository.buscarPorNombre(nombre);
    }

    public List<Heladera> buscarPorBarrio(Barrio barrio){return this.heladeraRepository.buscarPorBarrio(barrio);}
    public void guardarHeladera(Heladera heladera) {
        // TODO - validaciones??
        withTransaction(() -> this.heladeraRepository.guardar(heladera));
    }

    public void actualizarHeladera(Heladera heladeraActualizada) {
        beginTransaction();
        this.heladeraRepository.actualizar(heladeraActualizada);
        commitTransaction();
    }

    public void eliminarHeladera(Heladera heladera) {
        this.heladeraRepository.eliminar(heladera);
    }

    public boolean puedeConfigurar(Colaborador colaborador, Heladera heladera) {
        List<HacerseCargoHeladera> encargos = hacerseCargoHeladeraRepository.buscarPorColaborador(colaborador);
        return encargos.stream().anyMatch(encargo -> encargo.getHeladeraACargo().equals(heladera));
    }
}
