package ar.edu.utn.frba.dds.models.repositories.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteDePuntos;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

/**
 * Repositorio de variantes de puntos.
 */
public class VarianteDePuntosRepository implements WithSimplePersistenceUnit {

  /**
   * Guarda una variante de puntos.
   *
   * @param varianteDePuntos Variante de puntos a guardar
   */
  public void guardar(VarianteDePuntos varianteDePuntos) {
    entityManager().persist(varianteDePuntos);
  }

  /**
   * Busca una variante de puntos por su id.
   *
   * @param id Id de la variante de puntos
   */
  public Optional<VarianteDePuntos> buscarPorId(String id) {
    try {
      Long idLong = Long.parseLong(id);
      return Optional.ofNullable(entityManager().find(VarianteDePuntos.class, idLong));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca el último registro de variante de puntos.
   */
  public Optional<VarianteDePuntos> buscarUltimo() {
    String query = "from VarianteDePuntos v order by v.fechaConfiguracion desc";

    return Optional.ofNullable(entityManager()
        .createQuery(query, VarianteDePuntos.class)
        .setMaxResults(1)
        .getSingleResult());
  }
}
