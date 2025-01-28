package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.heladera.OperacionApertura;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.heladera.SolicitudDeAperturaRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de Solicitud de Apertura.
 */
public class SolicitudDeAperturaService {

  private final SolicitudDeAperturaRepository solicitudDeAperturaRepository;

  public SolicitudDeAperturaService(SolicitudDeAperturaRepository solicitudDeAperturaRepository) {
    this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
  }

  public List<SolicitudDeApertura> buscarPorTarjetaHeladeraEnLasUltimas(String tarjeta, Heladera heladera) {
    return this.solicitudDeAperturaRepository.buscarPorTarjetaHeladeraEnLasUltimas(tarjeta, heladera);
  }

  /**
   * Genera una solicitud de apertura para el ingreso de viandas.
   *
   * @param tarjeta  Tarjeta del Colaborador
   * @param heladera Heladera
   * @param motivo   Motivo de la apertura
   * @return Solicitud de Apertura
   */
  public SolicitudDeApertura generarParaIngresoDeViandas(TarjetaColaborador tarjeta,
                                                         Heladera heladera,
                                                         MotivoApertura motivo) {
    SolicitudDeApertura solicitud = SolicitudDeApertura
        .por(tarjeta, heladera, LocalDateTime.now(), motivo, OperacionApertura.INGRESO_VIANDAS);
    solicitudDeAperturaRepository.guardar(solicitud);
    return solicitud;
  }

  /**
   * Genera una solicitud de apertura para la extracción de viandas.
   *
   * @param tarjeta  Tarjeta del Colaborador
   * @param heladera Heladera
   * @param motivo   Motivo de la apertura
   * @return Solicitud de Apertura
   */
  public SolicitudDeApertura generarParaExtraccionDeViandas(TarjetaColaborador tarjeta,
                                                            Heladera heladera,
                                                            MotivoApertura motivo) {

    SolicitudDeApertura solicitud = SolicitudDeApertura
        .por(tarjeta, heladera, LocalDateTime.now(), motivo, OperacionApertura.RETIRO_VIANDAS);

    solicitudDeAperturaRepository.guardar(solicitud);
    return solicitud;
  }

}