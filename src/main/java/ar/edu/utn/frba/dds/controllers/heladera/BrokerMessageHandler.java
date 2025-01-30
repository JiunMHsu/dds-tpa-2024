package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.exceptions.AperturaDeHeladeraDenied;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.services.heladera.AperturaHeladeraService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.heladera.RetiroDeViandaService;
import ar.edu.utn.frba.dds.services.heladera.SolicitudDeAperturaService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Manejador de mensajes del broker.
 */
public class BrokerMessageHandler implements IBrokerMessageHandler {

  private final HeladeraService heladeraService;
  private final IncidenteService incidenteService;
  private final FallaHeladeraService fallaHeladeraService;
  private final SolicitudDeAperturaService solicitudDeAperturaService;
  private final TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;
  private final AperturaHeladeraService aperturaHeladeraService;
  private final RetiroDeViandaService retiroDeViandaService;

  /**
   * Constructor.
   *
   * @param heladeraService                 Servicio de heladera
   * @param incidenteService                Servicio de incidente
   * @param fallaHeladeraService            Servicio de falla de heladera
   * @param solicitudDeAperturaService      Servicio de solicitud de apertura
   * @param tarjetaPersonaVulnerableService Servicio de tarjeta de persona vulnerable
   * @param aperturaHeladeraService         Servicio de apertura de heladera
   * @param retiroDeViandaService           Servicio de retiro de vianda
   */
  public BrokerMessageHandler(HeladeraService heladeraService,
                              IncidenteService incidenteService,
                              FallaHeladeraService fallaHeladeraService,
                              SolicitudDeAperturaService solicitudDeAperturaService,
                              TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService,
                              AperturaHeladeraService aperturaHeladeraService,
                              RetiroDeViandaService retiroDeViandaService) {
    this.heladeraService = heladeraService;
    this.incidenteService = incidenteService;
    this.fallaHeladeraService = fallaHeladeraService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
    this.tarjetaPersonaVulnerableService = tarjetaPersonaVulnerableService;
    this.aperturaHeladeraService = aperturaHeladeraService;
    this.retiroDeViandaService = retiroDeViandaService;
  }

  @Override
  public void manejarTemperatura(double temperatura, UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString());

    if (!heladera.admiteTemperatura(temperatura)) {
      Incidente incidente = Incidente.fallaTemperatura(heladera, LocalDateTime.now());
      this.incidenteService.registrarIncidente(incidente);

      this.notificarPorFalla(heladera, incidente);
    } else {
      heladera.setUltimaTemperatura(temperatura);
      this.heladeraService.actualizarHeladera(heladera);
    }
  }

  @Override
  public void manejarFraude(UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString());

    Incidente incidente = Incidente.fraude(heladera, LocalDateTime.now());
    this.incidenteService.registrarIncidente(incidente);

    this.notificarPorFalla(heladera, incidente);
  }

  @Override
  public void manejarFallaConexion(UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString());

    Incidente incidente = Incidente.fallaConexion(heladera, LocalDateTime.now());
    this.incidenteService.registrarIncidente(incidente);

    this.notificarPorFalla(heladera, incidente);
  }

  private void notificarPorFalla(Heladera heladera, Incidente incidente) {
    List<SuscripcionFallaHeladera> suscripciones =
        this.fallaHeladeraService.obtenerPorHeladera(heladera);

    suscripciones.parallelStream()
        .forEach(s -> fallaHeladeraService.notificacionFallaHeladera(s, incidente));
  }

  @Override
  public void manejarSolicitudDeApertura(String codigoTarjeta, UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString());

    try {
      tarjetaPersonaVulnerableService.buscarTarjetaPorCodigo(codigoTarjeta).ifPresentOrElse(
          tarjeta -> manejarSolicitudPersonaVulnerable(tarjeta, heladera),
          () -> manejarSolicitudColaborador(codigoTarjeta, heladera)
      );
    } catch (AperturaDeHeladeraDenied e) {
      System.out.println("no se permite acceso");
    }
  }

  private void manejarSolicitudPersonaVulnerable(TarjetaPersonaVulnerable tarjeta,
                                                 Heladera healdera)
      throws AperturaDeHeladeraDenied {
    // TODO: Implementar
  }

  private void manejarSolicitudColaborador(String codigoTarjeta, Heladera heladera)
      throws AperturaDeHeladeraDenied {

    SolicitudDeApertura solicitudDeApertura = solicitudDeAperturaService.buscarPorTarjetaHeladeraEnLasUltimas(codigoTarjeta, heladera)
        .stream()
        .filter(solicitud -> this.aperturaHeladeraService.buscarPorSolicitud(solicitud).isEmpty()) //solicitudes que no tienen aperturas
        .min(Comparator.comparing(SolicitudDeApertura::getFechaHora))
        .orElseThrow(AperturaDeHeladeraDenied::new);

    // TODO: Implementar
  }
}
