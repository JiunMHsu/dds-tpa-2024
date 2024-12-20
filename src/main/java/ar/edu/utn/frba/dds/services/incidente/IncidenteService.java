package ar.edu.utn.frba.dds.services.incidente;

import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class IncidenteService implements WithSimplePersistenceUnit {

  private final IncidenteRepository incidenteRepository;
  private final HeladeraRepository heladeraRepository;

  public IncidenteService(IncidenteRepository incidenteRepository, HeladeraRepository heladeraRepository) {
    this.incidenteRepository = incidenteRepository;
    this.heladeraRepository = heladeraRepository;
  }

  public List<Incidente> buscarTodos() {
    return this.incidenteRepository.buscarTodos();
  }

  public List<Incidente> buscarTodasAlertas() {
    return this.incidenteRepository.buscarAlertas();
  }

  public List<Incidente> buscarTodasFallasTecnicas() {
    return this.incidenteRepository.buscarPorTipo(TipoIncidente.FALLA_TECNICA);
  }

  public Optional<Incidente> buscarIncidentePorId(String id) {
    return this.incidenteRepository.buscarPorId(id);
  }

  public void registrarIncidente(Incidente incidente) {
    if (incidente.getTipo().equals(TipoIncidente.FALLA_TECNICA) && incidente.getColaborador() == null) {
      throw new IllegalArgumentException("Las Fallas Tecnicas deben tener asociado un Colaborador");
    }

    incidente.getHeladera().setEstado(EstadoHeladera.INACTIVA);

    withTransaction(() -> {
      heladeraRepository.actualizar(incidente.getHeladera());
      incidenteRepository.guardar(incidente);
    });

    // TODO - avisar a técnicos, enviar respectivos mensajes
  }

  public void resolverIncidente(Incidente incidente) {
    incidente.setResuelta(true);

    if (!this.tieneOtroIncidentePendiente(incidente.getHeladera())) {
      incidente.getHeladera().setEstado(EstadoHeladera.ACTIVA);

      // TODO - reanudar los brokers
    }

    withTransaction(() -> {
      heladeraRepository.actualizar(incidente.getHeladera());
      incidenteRepository.actualizar(incidente);
    });
  }

  private boolean tieneOtroIncidentePendiente(Heladera heladera) {
    return incidenteRepository.buscarPorHeladera(heladera).stream()
        .filter(i -> !i.getResuelta())
        .toList().size() > 1; // mayor a 1 porque el incidente actual cuenta como pendiente
  }

}
