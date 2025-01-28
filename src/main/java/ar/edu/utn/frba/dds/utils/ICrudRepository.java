package ar.edu.utn.frba.dds.utils;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones básicas de un repositorio CRUD.
 *
 * @param <T> tipo de entidad
 */
public interface ICrudRepository<T> {
  void guardar(T entidad);

  void actualizar(T entidad);

  void eliminar(T entidad);

  Optional<T> buscarPorId(String id);

  List<T> buscarTodos();
}
