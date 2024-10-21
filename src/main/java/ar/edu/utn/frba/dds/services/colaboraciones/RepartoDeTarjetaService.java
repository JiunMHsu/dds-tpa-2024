package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public class RepartoDeTarjetaService {

    private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private final PersonaVulnerableRepository personaVulnerableRepository;
    private final TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;
    private final ColaboradorRepository colaboradorRepository;

    public RepartoDeTarjetaService(RepartoDeTarjetasRepository repartoDeTarjetasRepository,
                                   PersonaVulnerableRepository personaVulnerableRepository,
                                   TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository,
                                   ColaboradorRepository colaboradorRepository) {

        this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
        this.personaVulnerableRepository = personaVulnerableRepository;
        this.tarjetaPersonaVulnerableRepository = tarjetaPersonaVulnerableRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public void registrarReparto(Colaborador colaborador, PersonaVulnerable personaVulnerable, TarjetaPersonaVulnerable tarjeta) {

        Optional<Colaborador> colaboradorExistente = colaboradorRepository.buscarPorId(colaborador.getId().toString());
        if (colaboradorExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        Optional<PersonaVulnerable> personaExistente = personaVulnerableRepository.buscarPorId(personaVulnerable.getId().toString());
        if (personaExistente.isEmpty()) {
            throw new IllegalArgumentException("La persona vulnerable no existe en la base de datos");
        }

        Optional<TarjetaPersonaVulnerable> tarjetaExistente = tarjetaPersonaVulnerableRepository.obtenerPorCodigo(tarjeta.getCodigo());
        if (tarjetaExistente.isEmpty()) {
            throw new IllegalArgumentException("La tarjeta no existe en la base de datos");
        }

        RepartoDeTarjetas reparto = RepartoDeTarjetas.por(
                colaborador,
                LocalDateTime.now(),
                tarjeta,
                personaVulnerable
        );

        repartoDeTarjetasRepository.guardar(reparto);
    }
}
