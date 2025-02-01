package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.personaVulnerable.CreatePersonaVulnerableDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjeta;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Servicio de reparto de tarjetas.
 */
public class RepartoDeTarjetaService implements WithSimplePersistenceUnit {

  private final RepartoDeTarjetaRepository repartoDeTarjetasRepository;
  private final PersonaVulnerableRepository personaVulnerableRepository;
  private final TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;
  private final ColaboradorRepository colaboradorRepository;

  /**
   * Constructor.
   *
   * @param repartoDeTarjetasRepository Repositorio de reparto de tarjetas
   * @param personaVulnerableRepository Repositorio de personas vulnerables
   * @param tarjetaRepository           Repositorio de tarjetas de personas vulnerables
   * @param colaboradorRepository       Repositorio de colaboradores
   */
  public RepartoDeTarjetaService(RepartoDeTarjetaRepository repartoDeTarjetasRepository,
                                 PersonaVulnerableRepository personaVulnerableRepository,
                                 TarjetaPersonaVulnerableRepository tarjetaRepository,
                                 ColaboradorRepository colaboradorRepository) {

    this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
    this.personaVulnerableRepository = personaVulnerableRepository;
    this.tarjetaPersonaVulnerableRepository = tarjetaRepository;
    this.colaboradorRepository = colaboradorRepository;
  }

  /**
   * Registra un reparto de tarjeta.
   *
   * @param colaborador            Colaborador que realiza el reparto
   * @param nuevaPersonaVulnerable Datos de la persona vulnerable
   */
  public void registrarRepartoTarjeta(Colaborador colaborador,
                                      CreatePersonaVulnerableDTO nuevaPersonaVulnerable) {
    TipoDocumento tipo = nuevaPersonaVulnerable.getTipoDocumento() == null
        ? null
        : TipoDocumento.valueOf(nuevaPersonaVulnerable.getTipoDocumento().toUpperCase());

    Documento documento = Documento.con(tipo, nuevaPersonaVulnerable.getNroDocumento());

    final PersonaVulnerable personaVulnerable = PersonaVulnerable.con(
        nuevaPersonaVulnerable.getNombre(),
        documento,
        nuevaPersonaVulnerable.getFechaNacimiento(),
        LocalDate.now(),
        Direccion.con(
            new Barrio(nuevaPersonaVulnerable.getBarrio()),
            new Calle(nuevaPersonaVulnerable.getCalle()),
            nuevaPersonaVulnerable.getAltura()
        ),
        nuevaPersonaVulnerable.getMenoresACargo()
    );

    final TarjetaPersonaVulnerable tarjeta = TarjetaPersonaVulnerable.de(
        nuevaPersonaVulnerable.getTarjetaAsignada(),
        personaVulnerable
    );

    final RepartoDeTarjeta reparto = RepartoDeTarjeta
        .por(colaborador, LocalDateTime.now(), tarjeta, personaVulnerable);

    colaborador.invalidarPuntos();

    beginTransaction();
    personaVulnerableRepository.guardar(personaVulnerable);
    tarjetaPersonaVulnerableRepository.guardar(tarjeta);
    repartoDeTarjetasRepository.guardar(reparto);
    colaboradorRepository.actualizar(colaborador);
    commitTransaction();
  }
}
