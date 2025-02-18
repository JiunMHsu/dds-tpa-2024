package ar.edu.utn.frba.dds.models.repositories.mensajeria;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository de mensajes.
 */
public class MensajeRepository implements WithSimplePersistenceUnit {

  public void guardar(Mensaje mensaje) {
    entityManager().persist(mensaje);
  }

  /**
   * Busca un mensaje por su id.
   *
   * @param id el id del mensaje
   * @return un mensaje si existe, o vacío si no
   */
  public Optional<Mensaje> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(Mensaje.class, uuid));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  /**
   * Busca todos los mensajes.
   *
   * @return una lista con todos los mensajes
   */
  public List<Mensaje> buscarTodos() {
    return entityManager()
        .createQuery("from Mensaje", Mensaje.class)
        .getResultList();
  }

}
