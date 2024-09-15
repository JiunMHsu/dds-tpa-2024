package ar.edu.utn.frba.dds.repository.colaboracion;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface ColaboracionRepositoryI<E> extends WithSimplePersistenceUnit {
  List<E> obtenerTodos();

  List<E> obtenerPorColaborador(String nombre);

  E obtenerPorId(int id);
}
