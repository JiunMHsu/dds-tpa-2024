package ar.edu.utn.frba.dds.models.repositories.mensajeria;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.mensajeria.Mensaje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MensajeRepository implements WithSimplePersistenceUnit {

  public void guardar(Mensaje mensaje) {
    entityManager().persist(mensaje);
  }

  public Optional<Mensaje> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Mensaje.class, uuid));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public List<Mensaje> buscarTodos() {
    return entityManager()
        .createQuery("from Mensaje", Mensaje.class)
        .getResultList();
  }

  public List<Mensaje> buscarPorColaborador(Colaborador colaborador) {
    return entityManager()
        .createQuery("from Mensaje m where m.colaborador = :colaborador", Mensaje.class)
        .setParameter("colaborador", colaborador)
        .getResultList();
  }

  public List<Mensaje> buscarPorTecnico(Colaborador tecnico) {
    return entityManager()
        .createQuery("from Mensaje m where m.tecnico = :tecnico", Mensaje.class)
        .setParameter("tecnico", tecnico)
        .getResultList();
  }
}
