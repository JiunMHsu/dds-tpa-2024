package ar.edu.utn.frba.dds.models.repositories.reporte;

import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de reportes.
 */
public class ReporteRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda un reporte.
   *
   * @param reporte Reporte a guardar
   */
  public void guardar(Reporte reporte) {
    entityManager().persist(reporte);
  }

  /**
   * Busca un reporte por su id.
   *
   * @param id Id del reporte
   */
  public Optional<Reporte> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Reporte.class, uuid))
          .filter(Reporte::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca todos los reportes.
   */
  public List<Reporte> buscarTodos() {
    return entityManager()
        .createQuery("from Reporte r where r.alta = :alta", Reporte.class)
        .setParameter("alta", true)
        .getResultList();
  }
}
