package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import java.util.List;
import java.util.Optional;

public interface IHeladeraReporitory extends ICrudRepository<Heladera> {

    Optional<Heladera> buscarPorNombre(String nombre);

    List<Heladera> buscarPorBarrio(Barrio barrio);
}
