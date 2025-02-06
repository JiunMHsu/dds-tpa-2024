package ar.edu.utn.frba.dds.models.repositories.tecnico;

import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

/**
 * Repositorio de Técnico.
 */
public class TecnicoRepository implements WithSimplePersistenceUnit, ICrudRepository<Tecnico> {

  @Override
  public void guardar(Tecnico tecnico) {
    entityManager().persist(tecnico);
  }

  @Override
  public void actualizar(Tecnico tecnico) {
    withTransaction(() -> entityManager().merge(tecnico));
  }

  @Override
  public void eliminar(Tecnico tecnico) {
    withTransaction(() -> {
      tecnico.setAlta(false);
      entityManager().merge(tecnico);
    });
  }

  @Override
  public List<Tecnico> buscarTodos() {
    return entityManager()
        .createQuery("from Tecnico t where t.alta = :alta", Tecnico.class)
        .setParameter("alta", true)
        .getResultList();
  }

  @Override
  public Optional<Tecnico> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Tecnico.class, uuid))
          .filter(Tecnico::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca todos los Técnicos de un Barrio.
   *
   * @param barrio Barrio
   * @return Lista de Técnicos
   */
  public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
    return entityManager()
        .createQuery("from Tecnico t "
            + "where t.areaDeCobertura.barrio = :barrio and t.alta = :alta", Tecnico.class)
        .setParameter("barrio", barrio)
        .setParameter("alta", true)
        .getResultList();
  }

  /**
   * Busca un Técnico por su Usuario.
   *
   * @param usuario Usuario
   * @return Técnico
   */
  public Optional<Tecnico> buscarPorUsuario(Usuario usuario) {
    try {
      return Optional.of(
          entityManager()
              .createQuery("from Tecnico t "
                  + "where t.usuario = :usuario and t.alta = :alta", Tecnico.class)
              .setParameter("alta", true)
              .setParameter("usuario", usuario)
              .getSingleResult()
      );
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

}
