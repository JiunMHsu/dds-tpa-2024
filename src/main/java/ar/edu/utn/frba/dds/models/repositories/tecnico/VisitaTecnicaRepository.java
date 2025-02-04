package ar.edu.utn.frba.dds.models.repositories.tecnico;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.tecnico.VisitaTecnica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de visitas técnicas.
 */
public class VisitaTecnicaRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda una visita técnica.
   *
   * @param visitaTecnica Visita técnica
   */
  public void guardar(VisitaTecnica visitaTecnica) {
    entityManager().persist(visitaTecnica);
  }

  /**
   * Busca todas las visitas técnicas.
   *
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnica> buscarTodos() {
    return entityManager()
        .createQuery("from VisitaTecnica v where v.alta = :alta", VisitaTecnica.class)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca una visita técnica por su id.
   *
   * @param id Id de la visita técnica
   * @return Visita técnica
   */
  public Optional<VisitaTecnica> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(VisitaTecnica.class, uuid))
          .filter(VisitaTecnica::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca las visitas técnicas de un técnico.
   *
   * @param t Técnico
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnica> buscarPorTecnico(Tecnico t) {
    String query = "from VisitaTecnica v where v.tecnico = :tecnico and v.alta = :alta";

    return entityManager()
        .createQuery(query, VisitaTecnica.class)
        .setParameter("tecnico", t)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca las visitas técnicas de un incidente.
   *
   * @param i Incidente
   * @return Lista de visitas técnicas
   */
  public List<VisitaTecnica> buscarPorIncidente(Incidente i) {
    String query = "from VisitaTecnica v where v.incidente = :incidente and v.alta = :alta";

    return entityManager()
        .createQuery(query, VisitaTecnica.class)
        .setParameter("incidente", i)
        .setParameter("alta", true)
        .getResultList();
  }
}
