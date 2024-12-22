package ar.edu.utn.frba.dds.models.repositories.tecnico;

import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public class TecnicoRepository implements WithSimplePersistenceUnit {

  public void guardar(Tecnico tecnico) {
    entityManager().persist(tecnico);
  }

  public void actualizar(Tecnico tecnico) {
    withTransaction(() -> entityManager().merge(tecnico));
  }

  public void eliminar(Tecnico tecnico) {
    withTransaction(() -> {
      tecnico.setAlta(false);
      entityManager().merge(tecnico);
    });
  }

  public List<Tecnico> buscarTodos() {
    return entityManager()
        .createQuery("from Tecnico t where t.alta = :alta", Tecnico.class)
        .setParameter("alta", true)
        .getResultList();
  }

  public Optional<Tecnico> obtenerPorCuit(String cuit) {
    return Optional.ofNullable(entityManager()
        .createQuery("from Tecnico t where t.cuit = :cuit and t.alta = :alta", Tecnico.class)
        .setParameter("cuit", cuit)
        .setParameter("alta", true)
        .getSingleResult());
  }

  public List<Tecnico> obtenerPorBarrio(Barrio barrio) {
    return entityManager()
        .createQuery("from Tecnico t where t.areaDeCobertura.barrio = :barrio and t.alta = :alta", Tecnico.class)
        .setParameter("barrio", barrio)
        .setParameter("alta", true)
        .getResultList();
  }

  public Optional<Tecnico> buscarPorUsuario(Usuario u) {
    try {
      return Optional.of(
          entityManager()
              .createQuery("from Tecnico t where t.usuario = :usuario and t.alta = :alta", Tecnico.class)
              .setParameter("alta", true)
              .setParameter("usuario", u)
              .getSingleResult()
      );
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

}
