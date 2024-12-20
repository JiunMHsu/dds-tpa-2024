package ar.edu.utn.frba.dds.models.repositories.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class VisitaTecnicaRepository implements WithSimplePersistenceUnit {

  public void guardar(VisitaTecnica visitaTecnica) {
    entityManager().persist(visitaTecnica);
  }
}
