package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.exceptions.AperturaDeniedException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.services.colaboraciones.DistribucionViandasService;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionViandaService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.heladera.RetiroDeViandaService;
import ar.edu.utn.frba.dds.services.heladera.SolicitudDeAperturaService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FaltaViandaService;
import ar.edu.utn.frba.dds.services.suscripcion.HeladeraLlenaService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Manejador de mensajes del broker.
 */
@SuppressWarnings("checkstyle:Indentation")
public class BrokerMessageHandler implements IBrokerMessageHandler {

  private final HeladeraService heladeraService;
  private final IncidenteService incidenteService;
  private final FallaHeladeraService fallaHeladeraService;
  private final TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService;
  private final SolicitudDeAperturaService solicitudDeAperturaService;
  private final DistribucionViandasService distribucionViandasService;
  private final DonacionViandaService donacionViandaService;
  private final RetiroDeViandaService retiroDeViandaService;
  private final FaltaViandaService faltaViandaService;
  private final HeladeraLlenaService heladeraLlenaService;

  /**
   * Constructor.
   *
   * @param heladeraService                 Servicio de heladera
   * @param incidenteService                Servicio de incidente
   * @param fallaHeladeraService            Servicio de falla de heladera
   * @param solicitudDeAperturaService      Servicio de solicitud de apertura
   * @param tarjetaPersonaVulnerableService Servicio de tarjeta de persona vulnerable
   * @param retiroDeViandaService           Servicio de retiro de vianda
   * @param faltaViandaService              Servicio de falta de viandas
   * @param heladeraLlenaService            Servicio de heladera llena
   */
  public BrokerMessageHandler(HeladeraService heladeraService,
                              IncidenteService incidenteService,
                              FallaHeladeraService fallaHeladeraService,
                              SolicitudDeAperturaService solicitudDeAperturaService,
                              TarjetaPersonaVulnerableService tarjetaPersonaVulnerableService,
                              DistribucionViandasService distribucionViandasService,
                              DonacionViandaService donacionViandaService,
                              RetiroDeViandaService retiroDeViandaService,
                              FaltaViandaService faltaViandaService, HeladeraLlenaService heladeraLlenaService) {
    this.heladeraService = heladeraService;
    this.incidenteService = incidenteService;
    this.fallaHeladeraService = fallaHeladeraService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
    this.tarjetaPersonaVulnerableService = tarjetaPersonaVulnerableService;
    this.distribucionViandasService = distribucionViandasService;
    this.donacionViandaService = donacionViandaService;
    this.retiroDeViandaService = retiroDeViandaService;
    this.faltaViandaService = faltaViandaService;
    this.heladeraLlenaService = heladeraLlenaService;
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
    fallaHeladeraService.notificacionTecnico(incidente);
    fallaHeladeraService
        .obtenerPorHeladera(heladera)
        .parallelStream()
        .forEach(s -> fallaHeladeraService.notificacionColaborador(s, incidente));
  }

  @Override
  public void manejarSolicitudDeApertura(String codigoTarjeta, UUID heladeraId) {
    Heladera heladera = this.heladeraService.buscarPorId(heladeraId.toString());

    try {
      tarjetaPersonaVulnerableService.buscarTarjetaPorCodigo(codigoTarjeta).ifPresentOrElse(
          tarjeta -> manejarSolicitudPersonaVulnerable(tarjeta, heladera),
          () -> manejarSolicitudColaborador(codigoTarjeta, heladera)
      );
    } catch (AperturaDeniedException e) {
      System.out.println("no se permite acceso");
    }
  }

  private void manejarSolicitudPersonaVulnerable(TarjetaPersonaVulnerable tarjeta,
                                                 Heladera heladera)
      throws AperturaDeniedException {
    try {
      retiroDeViandaService.registrarRetiro(tarjeta, heladera);
      manejarFaltaVianda(heladera);
    } catch (Exception | CantidadDeViandasException e) {
      throw new AperturaDeniedException();
    }
  }

  private void manejarSolicitudColaborador(String codigoTarjeta, Heladera heladera)
      throws AperturaDeniedException {
    // TODO: estaVigente(3, ChronoUnit.HOURS) puede consumir de AppProperties

    SolicitudDeApertura solicitudDeApertura = solicitudDeAperturaService
        .buscarPorTarjetaHeladera(codigoTarjeta, heladera).stream()
        .filter(s -> s.estaVigente(3, ChronoUnit.HOURS))
        .findFirst()
        .orElseThrow(AperturaDeniedException::new);

    try {
      switch (solicitudDeApertura.getMotivo()) {
        case DISTRIBUCION_VIANDAS -> distribucionViandasService.efectuarAperturaPara(solicitudDeApertura);
        case DONACION_VIANDA -> donacionViandaService.efectuarAperturaPara(solicitudDeApertura);
        default -> throw new IllegalStateException();
      }
      this.manejarHeladeraLlena(heladera);
    } catch (ResourceNotFoundException | CantidadDeViandasException e) {
      throw new AperturaDeniedException();
    }
  }

  private void manejarFaltaVianda(Heladera heladera) {
    faltaViandaService.obtenerPorHeladera(heladera).stream()
        .filter(suscripcion -> heladera.getViandas() <= suscripcion.getUmbralViandas())
        .forEach(faltaViandaService::notificacionFaltaVianda);
  }

  private void manejarHeladeraLlena(Heladera heladera) {
    heladeraLlenaService.obtenerPorHeladera(heladera).stream()
        .filter(suscripcion -> heladera.getViandas() >= suscripcion.getUmbralEspacio())
        .forEach(heladeraLlenaService::notificacionHeladeraLlena);
  }
}
