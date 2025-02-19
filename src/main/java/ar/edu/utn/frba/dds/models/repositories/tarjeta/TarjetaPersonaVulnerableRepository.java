package ar.edu.utn.frba.dds.models.repositories.tarjeta;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

/**
 * Repositorio de tarjetas de personas vulnerables.
 */
public class TarjetaPersonaVulnerableRepository implements WithSimplePersistenceUnit {

  public void guardar(TarjetaPersonaVulnerable tarjeta) {
    entityManager().persist(tarjeta);
  }

  /**
   * Busca una tarjeta por su código.
   *
   * @param codigo el código de la tarjeta
   * @return la tarjeta, si existe
   */
  public Optional<TarjetaPersonaVulnerable> obtenerPorCodigo(String codigo) {
    return Optional.ofNullable(entityManager()
        .createQuery("from TarjetaPersonaVulnerable t where t.codigo =: codigo",
            TarjetaPersonaVulnerable.class)
        .setParameter("codigo", codigo)
        .getSingleResult());
  }

  public void eliminar(TarjetaPersonaVulnerable tarjeta) {
    withTransaction(() -> entityManager().remove(tarjeta));
  }

  /**
   * Busca una tarjeta por el id de la persona a la que pertenece.
   *
   * @param personaId el id de la persona
   * @return la tarjeta, si existe
   */
  public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorPersonaId(String personaId) {
    try {
      UUID uuid = UUID.fromString(personaId);
      return Optional.ofNullable(entityManager()
          .createQuery("from TarjetaPersonaVulnerable t where t.duenio.id = :personaId",
              TarjetaPersonaVulnerable.class)
          .setParameter("personaId", uuid)
          .getSingleResult());
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca una tarjeta por su código.
   *
   * @param codTarjeta el código de la tarjeta
   * @return la tarjeta, si existe
   */
  public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorCodigo(String codTarjeta) {
    try {
      return Optional.ofNullable(entityManager()
          .createQuery("from TarjetaPersonaVulnerable t where t.codigo = :codigo_tarjeta",
              TarjetaPersonaVulnerable.class)
          .setParameter("codigo_tarjeta", codTarjeta)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }


}
