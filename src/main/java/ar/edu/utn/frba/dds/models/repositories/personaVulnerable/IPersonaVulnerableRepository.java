package ar.edu.utn.frba.dds.models.repositories.personaVulnerable;

import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import java.util.Optional;

/**
 * Interfaz de Repositorio de personas vulnerables.
 */
public interface IPersonaVulnerableRepository extends ICrudRepository<PersonaVulnerable> {

  Optional<PersonaVulnerable> buscarPorDocumento(String documento);
}
