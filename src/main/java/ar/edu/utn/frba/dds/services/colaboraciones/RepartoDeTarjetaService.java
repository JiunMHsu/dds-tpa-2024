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

public class RepartoDeTarjetaService {

    private RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private PersonaVulnerableRepository personaVulnerableRepository;
    private TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;
    private ColaboradorRepository colaboradorRepository;

    public RepartoDeTarjetasService(RepartoDeTarjetasRepository repartoDeTarjetasRepository,
                                    PersonaVulnerableRepository personaVulnerableRepository,
                                    TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository,
                                    ColaboradorRepository colaboradorRepository) {

        this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
        this.personaVulnerableRepository = personaVulnerableRepository;
        this.tarjetaPersonaVulnerableRepository = tarjetaPersonaVulnerableRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public void registrarReparto(Colaborador colaborador, PersonaVulnerable personaVulnerable, TarjetaPersonaVulnerable tarjeta) {

        // TODO - Validacion de los datos (ver si existen en db)

        RepartoDeTarjetas reparto = RepartoDeTarjetas.por(
                colaborador,
                LocalDateTime.now(),
                tarjeta,
                personaVulnerable
        );

        repartoDeTarjetasRepository.guardar(reparto);
    }
}
