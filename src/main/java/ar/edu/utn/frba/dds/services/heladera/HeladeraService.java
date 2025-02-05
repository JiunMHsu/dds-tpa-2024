package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.dtos.heladera.CreateHeladeraDTO;
import ar.edu.utn.frba.dds.dtos.heladera.UpdateHeladeraDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.utils.AppProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
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

  /**
   * Busca heladeras por barrio.
   *
   * @param barrio Barrio
   * @return Lista de heladeras
   */
  public List<Heladera> buscarPorBarrio(Barrio barrio) {
    return this.heladeraRepository.buscarPorBarrio(barrio);
  }

  /**
   * Registra una nueva heladera. INACTIVA por defecto.
   *
   * @param nuevaHeladera DTO de heladera
   */
  public void registrar(CreateHeladeraDTO nuevaHeladera) {
    Direccion direccion = Direccion.con(
        new Barrio(nuevaHeladera.getBarrio()),
        new Calle(nuevaHeladera.getCalle()),
        nuevaHeladera.getAltura(),
        new Ubicacion(nuevaHeladera.getLatitud(), nuevaHeladera.getLongitud())
    );

    RangoTemperatura rangoTemperatura = new RangoTemperatura(
        nuevaHeladera.getTempMax(),
        nuevaHeladera.getTempMin()
    );

    String topic = AppProperties.getInstance().propertyFromName("BASE_TOPIC")
        + "/heladeras/"
        + nuevaHeladera.getNombre().toLowerCase().replace(" ", "-");


    Heladera heladera = Heladera.con(
        nuevaHeladera.getNombre(),
        direccion,
        nuevaHeladera.getCapacidad(),
        rangoTemperatura,
        topic
    );

    beginTransaction();
    this.heladeraRepository.guardar(heladera);
    commitTransaction();
  }

  /**
   * Actualiza una heladera.
   *
   * @param heladera    Heladera
   * @param actualizada DTO de heladera actualizada
   */
  public void actualizarHeladera(Heladera heladera, UpdateHeladeraDTO actualizada) {
    RangoTemperatura rango = new RangoTemperatura(
        actualizada.getTempMax(),
        actualizada.getTempMin()
    );

    heladera.setRangoTemperatura(rango);
    beginTransaction();
    this.heladeraRepository.actualizar(heladera);
    commitTransaction();
  }

  /**
   * Actualiza la temperatura de una heladera.
   *
   * @param heladera         Heladera
   * @param nuevaTemperatura Nueva temperatura
   */
  public void actualizarTemperatura(Heladera heladera, double nuevaTemperatura) {
    heladera.setUltimaTemperatura(nuevaTemperatura);

    beginTransaction();
    this.heladeraRepository.actualizar(heladera);
    commitTransaction();
  }

  public void eliminarHeladera(Heladera heladera) {
    this.heladeraRepository.eliminar(heladera);
  }

  public boolean puedeConfigurar(Colaborador colaborador, Heladera heladera) {
    List<HacerseCargoHeladera> encargos = hacerseCargoHeladeraRepository.buscarPorColaborador(colaborador);
    return encargos.stream().anyMatch(encargo -> encargo.getHeladera().equals(heladera));
  }
}

