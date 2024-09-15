package ar.edu.utn.frba.dds.repository.colaborador;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.util.Optional;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class ColaboradorRepository implements WithSimplePersistenceUnit {
  public void agregar(Colaborador colaborador) {
    entityManager().persist(colaborador);
  }

  public List<Colaborador> obtenerTodos() {
    return entityManager()
            .createQuery("from Colaborador", Colaborador.class)
            .getResultList();
  }
  public Colaborador buscarPorEmail(String email) {
    return entityManager().find(Colaborador.class, email);
  }
}
