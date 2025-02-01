package ar.edu.utn.frba.dds.services.incidente;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.services.mapa.MapService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

/**
 * Servicio de incidente.
 */
public class IncidenteService implements WithSimplePersistenceUnit {

  private final IncidenteRepository incidenteRepository;
  private final HeladeraRepository heladeraRepository;

  private final MapService mapService;

  /**
   * Constructor.
   *
   * @param incidenteRepository Repositorio de incidente
   * @param heladeraRepository  Repositorio de heladera
   * @param mapService          Servicio de mapa
   */
  public IncidenteService(IncidenteRepository incidenteRepository,
                          HeladeraRepository heladeraRepository,
                          MapService mapService) {
    this.incidenteRepository = incidenteRepository;
    this.heladeraRepository = heladeraRepository;
    this.mapService = mapService;
  }

  /**
   * Buscar todos los incidentes.
   * TODO: Mapear a DTO
   *
   * @return Lista de incidentes
   */
  public List<Incidente> buscarTodos() {
    return this.incidenteRepository.buscarTodos();
  }

  /**
   * Buscar todas las alertas.
   * TODO: Mapear a DTO
   *
   * @return Lista de incidentes
   */
  public List<Incidente> buscarTodasAlertas() {
    return this.incidenteRepository.buscarAlertas();
  }

  /**
   * Buscar todas las fallas técnicas.
   * TODO: Mapear a DTO
   *
   * @return Lista de incidentes
   */
  public List<Incidente> buscarTodasFallasTecnicas() {
    return this.incidenteRepository.buscarPorTipo(TipoIncidente.FALLA_TECNICA);
  }

  /**
   * Buscar todas las fallas de temperatura.
   * TODO: Mapear a DTO
   *
   * @return Lista de incidentes
   */
  public Incidente buscarIncidentePorId(String id) {
    return this.incidenteRepository
        .buscarPorId(id)
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Registrar un incidente.
   * TODO: Recibir un DTO
   *
   * @param incidente Incidente
   */
  public void registrarIncidente(Incidente incidente) {
    boolean esFallaTecnica = incidente.getTipo().equals(TipoIncidente.FALLA_TECNICA);
    boolean noTieneColaborador = incidente.getColaborador() == null;

    if (esFallaTecnica && noTieneColaborador) {
      throw new IllegalArgumentException("Las Fallas Tecnicas deben tener asociado un Colaborador");
    }

    incidente.getHeladera().setEstado(EstadoHeladera.INACTIVA);

    withTransaction(() -> {
      heladeraRepository.actualizar(incidente.getHeladera());
      incidenteRepository.guardar(incidente);
    });
  }

  /**
   * Resolver un incidente.
   * TODO: Recibir un DTO
   * TODO: Reanudar los brokers
   *
   * @param incidente Incidente
   */
  public void resolverIncidente(Incidente incidente) {
    incidente.setEsResuelta(true);

    if (!this.tieneOtroIncidentePendiente(incidente.getHeladera())) {
      incidente.getHeladera().setEstado(EstadoHeladera.ACTIVA);

      // Reanudar brokers
      // Esta en HeladeraService, ver si moverlo a otro lado
    }

    withTransaction(() -> {
      heladeraRepository.actualizar(incidente.getHeladera());
      incidenteRepository.actualizar(incidente);
    });
  }

  private boolean tieneOtroIncidentePendiente(Heladera heladera) {
    return incidenteRepository.buscarPorHeladera(heladera).stream()
        .filter(i -> !i.getEsResuelta())
        .toList().size() > 1; // mayor a 1 porque el incidente actual cuenta como pendiente
  }
}
