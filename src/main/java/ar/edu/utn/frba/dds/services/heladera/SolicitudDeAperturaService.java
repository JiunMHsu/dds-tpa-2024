package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.EstadoSolicitud;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.OperacionApertura;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.heladera.SolicitudDeAperturaRepository;
import java.util.List;

/**
 * Servicio de Solicitud de Apertura.
 */
public class SolicitudDeAperturaService {

  private final SolicitudDeAperturaRepository solicitudDeAperturaRepository;

  public SolicitudDeAperturaService(SolicitudDeAperturaRepository solicitudDeAperturaRepository) {
    this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
  }

  public void actualizar(SolicitudDeApertura solicitudDeApertura) {
    this.solicitudDeAperturaRepository.actualizar(solicitudDeApertura);
  }

  public List<SolicitudDeApertura> buscarPorTarjetaHeladera(String tarjeta,
                                                            Heladera heladera) {
    return this.solicitudDeAperturaRepository.buscarPorTarjetaHeladera(tarjeta, heladera);
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
    SolicitudDeApertura solicitud = switch (motivo) {
      case DONACION_VIANDA -> SolicitudDeApertura.paraDonacionViandas(tarjeta, heladera);
      case DISTRIBUCION_VIANDAS -> SolicitudDeApertura.paraDistribucionDeViandas(
          tarjeta,
          heladera,
          OperacionApertura.INGRESO_VIANDAS);
    };

    solicitudDeAperturaRepository.guardar(solicitud);
    return solicitud;
  }

  /**
   * Genera una solicitud de apertura para la extracción de viandas.
   * No necesita un motivo, ya que siempre es para distribución de viandas.
   *
   * @param tarjeta  Tarjeta del Colaborador
   * @param heladera Heladera
   * @return Solicitud de Apertura
   */
  public SolicitudDeApertura generarParaExtraccionDeViandas(TarjetaColaborador tarjeta,
                                                            Heladera heladera) {

    SolicitudDeApertura solicitud = SolicitudDeApertura.paraDistribucionDeViandas(
        tarjeta,
        heladera,
        OperacionApertura.RETIRO_VIANDAS);

    solicitudDeAperturaRepository.guardar(solicitud);
    return solicitud;
  }

  public void completarSolicitud(SolicitudDeApertura solicitud) {
    solicitud.setEstado(EstadoSolicitud.COMPLETADA);
    solicitudDeAperturaRepository.actualizar(solicitud);
  }

}