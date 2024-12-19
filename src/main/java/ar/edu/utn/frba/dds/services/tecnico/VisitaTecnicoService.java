package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import ar.edu.utn.frba.dds.models.repositories.tecnico.VisitaTecnicoRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class VisitaTecnicoService implements WithSimplePersistenceUnit {

  private final VisitaTecnicoRepository visitaTecnicoRepository;

  public VisitaTecnicoService(VisitaTecnicoRepository visitaTecnicoRepository) {
    this.visitaTecnicoRepository = visitaTecnicoRepository;
  }

  public void registrarVisita(VisitaTecnico visitaTecnico) {
    this.visitaTecnicoRepository.guardar(visitaTecnico);
  }
}
