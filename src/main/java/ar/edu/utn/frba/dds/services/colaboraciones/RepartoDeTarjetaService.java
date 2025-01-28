package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjeta;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetaRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepartoDeTarjetaService implements WithSimplePersistenceUnit {

  private final RepartoDeTarjetaRepository repartoDeTarjetasRepository;
  private final PersonaVulnerableRepository personaVulnerableRepository;
  private final TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;

  public RepartoDeTarjetaService(RepartoDeTarjetaRepository repartoDeTarjetasRepository,
                                 PersonaVulnerableRepository personaVulnerableRepository,
                                 TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository) {

    this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
    this.personaVulnerableRepository = personaVulnerableRepository;
    this.tarjetaPersonaVulnerableRepository = tarjetaPersonaVulnerableRepository;
  }

  public void registrar(RepartoDeTarjeta reparto) {
    beginTransaction();
    personaVulnerableRepository.guardar(reparto.getPersonaVulnerable());
    tarjetaPersonaVulnerableRepository.guardar(reparto.getTarjeta());
    repartoDeTarjetasRepository.guardar(reparto);
    commitTransaction();
  }
}
