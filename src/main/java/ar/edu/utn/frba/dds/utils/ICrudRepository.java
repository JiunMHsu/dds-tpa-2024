package ar.edu.utn.frba.dds.utils;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository<T> {
    void guardar(T entidad);

    void actualizar(T entidad);

    void eliminar(T entidad);

    Optional<T> buscarPorId(String id);

    List<T> buscarTodos();
}
