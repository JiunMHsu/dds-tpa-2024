package ar.edu.utn.frba.dds.utils;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones básicas de un repositorio CRUD.
 *
 * @param <T> tipo de entidad
 */
//TODO check mayuscula
public interface ICrudRepository<T> {

  /**
   * Guarda una entidad en el repositorio.
   *
   * @param entidad la entidad a guardar
   */
  void guardar(T entidad);

  /**
   * Actualiza una entidad existente en el repositorio.
   *
   * @param entidad la entidad a actualizar
   */
  void actualizar(T entidad);

  /**
   * Elimina una entidad del repositorio.
   *
   * @param entidad la entidad a eliminar
   */
  void eliminar(T entidad);

  /**
   * Busca una entidad por su identificador.
   *
   * @param id el identificador de la entidad a buscar
   * @return un Optional que contiene la entidad encontrada, o vacío si no se encuentra
   */
  Optional<T> buscarPorId(String id);

  /**
   * Busca todas las entidades en el repositorio.
   *
   * @return una lista con todas las entidades
   */
  List<T> buscarTodos();
}
