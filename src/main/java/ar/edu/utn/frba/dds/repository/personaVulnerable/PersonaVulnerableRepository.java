package ar.edu.utn.frba.dds.repository.personaVulnerable;

import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class PersonaVulnerableRepository implements WithSimplePersistenceUnit {

    public void agregar(PersonaVulnerable personaVulnerable) {
        withTransaction(() -> entityManager().persist(personaVulnerable));
    }

}
