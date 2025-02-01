package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Servicio de heladera.
 */
public class HeladeraService implements WithSimplePersistenceUnit {

  private final HeladeraRepository heladeraRepository;
  private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

  /**
   * Constructor.
   *
   * @param heladeraRepository             Repositorio de heladera
   * @param hacerseCargoHeladeraRepository Repositorio de hacerse cargo de heladera
   */
  public HeladeraService(HeladeraRepository heladeraRepository,
                         HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository) {
    this.heladeraRepository = heladeraRepository;
    this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
  }

  /**
   * Busca todas las heladeras.
   *
   * @return Lista de heladeras
   */
  public List<Heladera> buscarTodas() {
    return this.heladeraRepository.buscarTodos();
  }

  /**
   * Busca una heladera por su ID.
   *
   * @param id Id de la heladera
   * @return Heladera
   */
  public Heladera buscarPorId(@NotNull String id) {
    return this.heladeraRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new);
  }

  public Optional<Heladera> buscarPorNombre(String nombre) {
    return this.heladeraRepository.buscarPorNombre(nombre);
  }

  public List<Heladera> buscarPorBarrio(Barrio barrio) {
    return this.heladeraRepository.buscarPorBarrio(barrio);
  }

  public void guardarHeladera(Heladera heladera) {
    withTransaction(() -> this.heladeraRepository.guardar(heladera));
  }

  public void actualizarHeladera(Heladera heladeraActualizada) {
    withTransaction(() -> this.heladeraRepository.actualizar(heladeraActualizada));
  }

  public void eliminarHeladera(Heladera heladera) {
    this.heladeraRepository.eliminar(heladera);
  }

  public boolean puedeConfigurar(Colaborador colaborador, Heladera heladera) {
    List<HacerseCargoHeladera> encargos = hacerseCargoHeladeraRepository.buscarPorColaborador(colaborador);
    return encargos.stream().anyMatch(encargo -> encargo.getHeladera().equals(heladera));
  }
}

