package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.models.repositories.tecnico.VisitaTecnicaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class VisitaTecnicaService implements WithSimplePersistenceUnit {

  private final VisitaTecnicaRepository visitaTecnicaRepository;

  public VisitaTecnicaService(VisitaTecnicaRepository visitaTecnicaRepository) {
    this.visitaTecnicaRepository = visitaTecnicaRepository;
  }

  public void registrarVisita(VisitaTecnica visitaTecnica) {
    withTransaction(() -> this.visitaTecnicaRepository.guardar(visitaTecnica));
  }

  public List<VisitaTecnica> buscarTodas() {
    return this.visitaTecnicaRepository.buscarTodos();
  }

  public Optional<VisitaTecnica> buscarPorId(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("El id no puede ser nulo o vacío");
    }
    return this.visitaTecnicaRepository.buscarPorId(id);
  }

  public List<VisitaTecnica> buscarPorTecnico(Tecnico tecnico) {
    return this.visitaTecnicaRepository.buscarPorTecnico(tecnico);
  }

  public List<VisitaTecnica> buscarPorincidente(Incidente incidente) {
    return this.visitaTecnicaRepository.buscarPorIncidente(incidente);
  }
}
