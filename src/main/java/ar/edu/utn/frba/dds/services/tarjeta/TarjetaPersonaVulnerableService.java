package ar.edu.utn.frba.dds.services.tarjeta;

import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import java.util.Optional;

/**
 * Servicio de Tarjeta de Persona Vulnerable.
 */
public class TarjetaPersonaVulnerableService {

  private final TarjetaPersonaVulnerableRepository tarjetaPersonaVulnerableRepository;

  /**
   * Constructor para inicializar el servicio de tarjetas de personas vulnerables.
   *
   * @param tarjetaRepository El repositorio de tarjetas de personas vulnerables.
   */
  public TarjetaPersonaVulnerableService(TarjetaPersonaVulnerableRepository tarjetaRepository) {
    this.tarjetaPersonaVulnerableRepository = tarjetaRepository;
  }

  /**
   * Busca una tarjeta por su código.
   *
   * @param codigo Código de la tarjeta.
   * @return Tarjeta encontrada.
   */
  public Optional<TarjetaPersonaVulnerable> buscarTarjetaPorCodigo(String codigo) {
    return tarjetaPersonaVulnerableRepository.buscarTarjetaPorCodigo(codigo);
  }
}
