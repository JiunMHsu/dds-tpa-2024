package ar.edu.utn.frba.dds.services.tarjeta;

import ar.edu.utn.frba.dds.exceptions.PersonaVulnerableNotFoundException;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import java.util.Optional;

public class TarjetaPersonaVulnerableService {

    private final TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;

    public TarjetaPersonaVulnerableService(TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository) {
        this.tarjetaPersonaVulnerableRepository = tarjetaPersonaVulnerableRepository;
    }

    // Ver si esta bn el nombre del metodo...
    public TarjetaPersonaVulnerable registrarTarjetaPV(String codigo, PersonaVulnerable personaAsociada) {

        System.out.println("Antes del if service tarjeta");

        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("El código por la tarjeta no puede ser nulo o vacío.");
        }

        System.out.println("Desp del if service tarjeta");

        TarjetaPersonaVulnerable nuevaTarjeta = TarjetaPersonaVulnerable.de(codigo, personaAsociada);

        this.tarjetaPersonaVulnerableRepository.guardar(nuevaTarjeta);

        return nuevaTarjeta;
    }

    public void eliminarTarjetaPorPersonaId(String personaId) {
        Optional<TarjetaPersonaVulnerable> tarjeta = this.tarjetaPersonaVulnerableRepository.buscarTarjetaPorPersonaId(personaId);

        if (tarjeta.isPresent()) {
            this.tarjetaPersonaVulnerableRepository.eliminar(tarjeta.get());
        } else {
            throw new PersonaVulnerableNotFoundException("Tarjeta paraColaborador persona vulnerable paraColaborador ID " + personaId + " no encontrada");
        }
    }

    public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorCodigo(String cod_tarjeta) {
        return this.buscarTarjetaPorCodigo(cod_tarjeta);
    }

}
