package ar.edu.utn.frba.dds.models.repositories.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class VisitaTecnicoRepository implements WithSimplePersistenceUnit {

  public void guardar(VisitaTecnico visitaTecnico) {
    withTransaction(() -> entityManager().persist(visitaTecnico));
  }
}
