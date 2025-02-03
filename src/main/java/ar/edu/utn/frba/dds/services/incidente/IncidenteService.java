package ar.edu.utn.frba.dds.services.incidente;

import ar.edu.utn.frba.dds.dtos.incidente.AlertaDTO;
import ar.edu.utn.frba.dds.dtos.incidente.IncidenteDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.services.heladera.SuscriptorSensorService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

/**
 * Servicio de incidente.
 */
public class IncidenteService implements WithSimplePersistenceUnit {

  private final IncidenteRepository incidenteRepository;
  private final HeladeraRepository heladeraRepository;
  private final SuscriptorSensorService suscriptorSensorService;

  /**
   * Constructor.
   *
   * @param incidenteRepository     Repositorio de incidente
   * @param heladeraRepository      Repositorio de heladera
   * @param suscriptorSensorService Servicio de suscriptor de sensor
   */
  public IncidenteService(IncidenteRepository incidenteRepository,
                          HeladeraRepository heladeraRepository,
                          SuscriptorSensorService suscriptorSensorService) {
    this.incidenteRepository = incidenteRepository;
    this.heladeraRepository = heladeraRepository;
    this.suscriptorSensorService = suscriptorSensorService;
  }

  /**
   * Buscar todos los incidentes.
   *
   * @return Lista de incidentes
   */
  public List<IncidenteDTO> buscarTodos() {
    return this.incidenteRepository.buscarTodos().stream()
        .map(IncidenteDTO::fromIncidente).toList();
  }

  /**
   * Buscar todas las alertas.
   *
   * @return Lista de incidentes
   */
  public List<AlertaDTO> buscarTodasAlertas() {
    return this.incidenteRepository.buscarAlertas().stream()
        .map(AlertaDTO::fromIncidente).toList();
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
   *
   * @param incidente Incidente
   */
  public void registrarIncidente(Incidente incidente) {
    boolean esFallaTecnica = incidente.getTipo().equals(TipoIncidente.FALLA_TECNICA);
    boolean noTieneColaborador = incidente.getColaborador() == null;

    if (esFallaTecnica && noTieneColaborador) {
      throw new IllegalArgumentException("Las Fallas Tecnicas deben tener asociado un Colaborador");
    }

    Heladera heladera = incidente.getHeladera();

    heladera.setEstado(EstadoHeladera.INACTIVA);
    suscriptorSensorService.desuscribirPara(heladera);

    beginTransaction();
    heladeraRepository.actualizar(heladera);
    incidenteRepository.guardar(incidente);
    commitTransaction();
  }

  /**
   * Resolver un incidente.
   *
   * @param incidente Incidente
   */
  public void resolverIncidente(Incidente incidente) {
    incidente.setEsResuelta(true);
    Heladera heladera = incidente.getHeladera();

    beginTransaction();
    incidenteRepository.actualizar(incidente);

    if (!this.tieneOtroIncidentePendiente(heladera)) {
      heladera.setEstado(EstadoHeladera.ACTIVA);
      suscriptorSensorService.suscribirPara(heladera);
      heladeraRepository.actualizar(heladera);
    }
    commitTransaction();
  }

  private boolean tieneOtroIncidentePendiente(Heladera heladera) {
    return incidenteRepository.buscarPorHeladera(heladera).stream()
        .filter(i -> !i.getEsResuelta())
        .toList().size() > 1; // mayor a 1 porque el incidente actual cuenta como pendiente
  }
}
