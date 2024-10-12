package ar.edu.utn.frba.dds.services.tarjeta;

import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;

public class TarjetaPersonaVulnerableService {

    private final TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;

    public TarjetaPersonaVulnerableService (TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository) {
        this.tarjetaPersonaVulnerableRepository = tarjetaPersonaVulnerableRepository;
    }

    // Ver si esta bn el nombre del metodo...
    public TarjetaPersonaVulnerable registrarTarjetaPV (String codigo, PersonaVulnerable personaAsociada) {

        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código de la tarjeta no puede ser nulo o vacío.");
        }

        TarjetaPersonaVulnerable nuevaTarjeta = TarjetaPersonaVulnerable.de(codigo, personaAsociada);

        this.tarjetaPersonaVulnerableRepository.guardar(nuevaTarjeta);

        return nuevaTarjeta;
    }
}
