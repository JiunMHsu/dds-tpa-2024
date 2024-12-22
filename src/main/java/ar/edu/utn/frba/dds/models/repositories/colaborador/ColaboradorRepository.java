package ar.edu.utn.frba.dds.models.repositories.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.NoResultException;

public class ColaboradorRepository implements IColaboradorRepository, WithSimplePersistenceUnit {

  @Override
  public void guardar(Colaborador colaborador) {
    entityManager().persist(colaborador);
  }

  @Override
  public void actualizar(Colaborador colaborador) {
    entityManager().merge(colaborador);
  }

  @Override
  public void eliminar(Colaborador colaborador) {
    withTransaction(() -> {
      colaborador.setAlta(false);
      entityManager().merge(colaborador);
    });
  }

  @Override
  public Optional<Colaborador> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Colaborador.class, uuid))
          .filter(Colaborador::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public Optional<Colaborador> buscarPorEmail(String email) {
    try {
      return Optional.of(entityManager()
          .createQuery("from Colaborador c where c.alta = :alta and c.usuario.email = :email", Colaborador.class)
          .setParameter("alta", true)
          .setParameter("email", email)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  public Optional<Colaborador> buscarPorUsuario(Usuario usuario) {
    try {
      return Optional.of(entityManager()
          .createQuery("from Colaborador c where c.alta = :alta and c.usuario = :usuario", Colaborador.class)
          .setParameter("alta", true)
          .setParameter("usuario", usuario)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  public List<Colaborador> buscarTodos() {
    return entityManager()
        .createQuery("from Colaborador c where c.alta = :alta", Colaborador.class)
        .setParameter("alta", true)
        .getResultList();
  }
}
