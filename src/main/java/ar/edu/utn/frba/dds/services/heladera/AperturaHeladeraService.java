package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.AperturaHeladera;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.repositories.heladera.AperturaHeladeraRepository;
import java.time.LocalDateTime;

/**
 * Servicio de apertura de heladera.
 */
public class AperturaHeladeraService {

  private final AperturaHeladeraRepository aperturaHeladeraRepository;

  public AperturaHeladeraService(AperturaHeladeraRepository aperturaHeladeraRepository) {
    this.aperturaHeladeraRepository = aperturaHeladeraRepository;
  }

  /**
   * Registra la apertura de una heladera.
   *
   * @param solicitudDeApertura Solicitud de apertura.
   */
  public void registrarApertura(SolicitudDeApertura solicitudDeApertura) {
    AperturaHeladera apertura = AperturaHeladera.por(
        solicitudDeApertura.getTarjeta(),
        solicitudDeApertura.getHeladera(),
        LocalDateTime.now(),
        solicitudDeApertura
    );
    this.aperturaHeladeraRepository.guardar(apertura);
  }

}
