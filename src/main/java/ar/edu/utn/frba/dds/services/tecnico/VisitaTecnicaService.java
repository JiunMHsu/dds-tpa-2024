package ar.edu.utn.frba.dds.services.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import ar.edu.utn.frba.dds.models.repositories.tecnico.VisitaTecnicaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class VisitaTecnicaService implements WithSimplePersistenceUnit {

  private final VisitaTecnicaRepository visitaTecnicaRepository;

  public VisitaTecnicaService(VisitaTecnicaRepository visitaTecnicaRepository) {
    this.visitaTecnicaRepository = visitaTecnicaRepository;
  }

  public void registrarVisita(VisitaTecnica visitaTecnica) {
    this.visitaTecnicaRepository.guardar(visitaTecnica);
  }
}
