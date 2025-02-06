package ar.edu.utn.frba.dds.models.repositories.tarjeta;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de Tarjeta de Colaborador.
 */
public class TarjetaColaboradorRepository
    implements ICrudRepository<TarjetaColaborador>, WithSimplePersistenceUnit {

  @Override
  public void guardar(TarjetaColaborador tarjeta) {
    entityManager().persist(tarjeta);
  }

  @Override
  public void actualizar(TarjetaColaborador tarjeta) {
    withTransaction(() -> entityManager().merge(tarjeta));
  }

  @Override
  public void eliminar(TarjetaColaborador tarjeta) {
    withTransaction(() -> {
      tarjeta.setAlta(false);
      entityManager().merge(tarjeta);
    });
  }

  @Override
  public Optional<TarjetaColaborador> buscarPorId(String id) {
    try {
      UUID uuid = UUID.fromString(id);
      return Optional.ofNullable(entityManager().find(TarjetaColaborador.class, uuid))
          .filter(TarjetaColaborador::getAlta);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<TarjetaColaborador> buscarTodos() {
    return entityManager()
        .createQuery("from TarjetaColaborador", TarjetaColaborador.class)
        .getResultList();
  }

  /**
   * Obtiene una tarjeta de colaborador por su código.
   *
   * @param codigo Código de la tarjeta
   * @return Tarjeta de colaborador
   */
  public Optional<TarjetaColaborador> obtenerPorCodigo(String codigo) {
    String query = "from TarjetaColaborador t where t.codigo = :codigo and t.alta = :alta";

    return Optional.ofNullable(entityManager()
        .createQuery(query, TarjetaColaborador.class)
        .setParameter("codigo", codigo)
        .setParameter("alta", true)
        .getSingleResult());
  }

  /**
   * Obtiene una tarjeta de por su colaborador.
   *
   * @param colaborador Colaborador
   * @return Tarjeta de colaborador
   */
  public Optional<TarjetaColaborador> buscarPorColaborador(Colaborador colaborador) {
    String query = "from TarjetaColaborador t where t.duenio = :colaborador and t.alta = :alta";

    return Optional.ofNullable(entityManager()
        .createQuery(query, TarjetaColaborador.class)
        .setParameter("colaborador", colaborador)
        .setParameter("alta", true)
        .getSingleResult());
  }
}
