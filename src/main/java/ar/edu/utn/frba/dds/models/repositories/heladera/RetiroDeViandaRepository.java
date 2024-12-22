package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RetiroDeViandaRepository implements WithSimplePersistenceUnit {

  public void guardar(RetiroDeVianda retiroDeVianda) {
    entityManager().persist(retiroDeVianda);
  }

  public Optional<RetiroDeVianda> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(RetiroDeVianda.class, uuid))
          .filter(RetiroDeVianda::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public List<RetiroDeVianda> buscarTodos() {
    return entityManager()
        .createQuery("from RetiroDeVianda", RetiroDeVianda.class)
        .getResultList();
  }

  public List<RetiroDeVianda> buscarAPartirDe(LocalDateTime fechaHora) {
    return fechaHora == null
        ? buscarTodos()
        : entityManager()
        .createQuery("from RetiroDeVianda r where r.alta = :alta and r.fechaHora >= :fecha", RetiroDeVianda.class)
        .setParameter("fecha", fechaHora)
        .setParameter("alta", true)
        .getResultList();
  }
}
