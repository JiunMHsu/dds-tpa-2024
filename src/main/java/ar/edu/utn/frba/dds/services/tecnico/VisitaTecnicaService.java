package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.dtos.tecnico.VisitaTecnicaDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.models.repositories.tecnico.VisitaTecnicaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Servicio de visita técnica.
 */
public class VisitaTecnicaService implements WithSimplePersistenceUnit {

  private final VisitaTecnicaRepository visitaTecnicaRepository;

  /**
   * Constructor.
   *
   * @param visitaTecnicaRepository Repositorio de visita técnica
   */
  public VisitaTecnicaService(VisitaTecnicaRepository visitaTecnicaRepository) {
    this.visitaTecnicaRepository = visitaTecnicaRepository;
  }

  /**
   * Registra una visita técnica.
   *
   * @param visitaTecnica Visita técnica
   */
  public void registrarVisita(VisitaTecnica visitaTecnica) {
    withTransaction(() -> this.visitaTecnicaRepository.guardar(visitaTecnica));
  }

  /**
   * Busca todas las visitas técnicas.
   *
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnicaDTO> buscarTodas() {
    return this.visitaTecnicaRepository
        .buscarTodos()
        .stream().map(VisitaTecnicaDTO::fromVisitaTecnica).toList();
  }

  /**
   * Busca una visita técnica por su ID.
   *
   * @param id Id de la visita técnica
   * @return Visita técnica
   */
  public VisitaTecnica buscarPorId(@NotNull String id) {
    return this.visitaTecnicaRepository.buscarPorId(id)
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Busca todas las visitas técnicas asociadas a un técnico.
   *
   * @param tecnico Técnico
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnicaDTO> buscarPorTecnico(Tecnico tecnico) {
    return this.visitaTecnicaRepository
        .buscarPorTecnico(tecnico)
        .stream().map(VisitaTecnicaDTO::fromVisitaTecnica).toList();
  }

  /**
   * Busca todas las visitas técnicas asociadas a un incidente.
   *
   * @param incidente Incidente
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnicaDTO> buscarPorincidente(Incidente incidente) {
    return this.visitaTecnicaRepository
        .buscarPorIncidente(incidente)
        .stream().map(VisitaTecnicaDTO::fromVisitaTecnica).toList();
  }
}
