package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.AperturaHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AperturaHeladeraRepository implements
    ICrudRepository<AperturaHeladera>,
    WithSimplePersistenceUnit {

  @Override
  public void guardar(AperturaHeladera apertura) {
    withTransaction(() -> entityManager().persist(apertura));
  }

  @Override
  public void actualizar(AperturaHeladera apertura) {
    withTransaction(() -> entityManager().merge(apertura));
  }

  @Override
  public void eliminar(AperturaHeladera apertura) {
    withTransaction(() -> {
      apertura.setAlta(false);
      entityManager().merge(apertura);
    });
  }

  @Override
  public Optional<AperturaHeladera> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(AperturaHeladera.class, uuid))
          .filter(AperturaHeladera::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<AperturaHeladera> buscarTodos() {
    return entityManager()
        .createQuery("from AperturaHeladera", AperturaHeladera.class)
        .getResultList();
  }

  public Optional<AperturaHeladera> buscarPorSolicitud(SolicitudDeApertura solicitudDeApertura) {
    try {
      return Optional.of(entityManager()
          .createQuery("from AperturaHeladera a " +
                  "where a.solicitudDeApertura = :solicitud_apertura ",
              AperturaHeladera.class)
          .setParameter("solicitud_apertura", solicitudDeApertura)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

}
