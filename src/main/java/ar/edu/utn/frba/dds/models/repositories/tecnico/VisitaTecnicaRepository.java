package ar.edu.utn.frba.dds.models.repositories.tecnico;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VisitaTecnicaRepository implements WithSimplePersistenceUnit {

  public void guardar(VisitaTecnica visitaTecnica) {
    entityManager().persist(visitaTecnica);
  }

  public List<VisitaTecnica> buscarTodos() {
    return entityManager()
        .createQuery("from VisitaTecnica v where v.alta = :alta", VisitaTecnica.class)
        .setParameter("alta", true)
        .getResultList();
  }

  public Optional<VisitaTecnica> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(VisitaTecnica.class, uuid))
          .filter(VisitaTecnica::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public List<VisitaTecnica> buscarPorTecnico(Tecnico t) {
    return entityManager()
        .createQuery("from VisitaTecnica v where v.tecnico = :tecnico and v.alta = :alta", VisitaTecnica.class)
        .setParameter("tecnico", t)
        .setParameter("alta", true)
        .getResultList();
  }

  public List<VisitaTecnica> buscarPorIncidente(Incidente i) {
    return entityManager()
        .createQuery("from VisitaTecnica v where v.incidente = :incidente and v.alta = :alta", VisitaTecnica.class)
        .setParameter("incidente", i)
        .setParameter("alta", true)
        .getResultList();
  }
}
