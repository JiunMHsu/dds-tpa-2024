package ar.edu.utn.frba.dds.repository.colaboracion;

import java.util.List;

public interface ColaboracionRepository<E> {
  List<E> obtenerTodos();

  List<E> obtenerPorColaborador(String nombre);

  E obtenerPorId(int id);
}
