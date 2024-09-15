package ar.edu.utn.frba.dds.repository.tecnico;

import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import java.util.List;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;

@Getter
public class TecnicoRepository implements WithSimplePersistenceUnit {
  public void agregar(Tecnico tecnico) {
    entityManager().persist(tecnico);
  }

}
