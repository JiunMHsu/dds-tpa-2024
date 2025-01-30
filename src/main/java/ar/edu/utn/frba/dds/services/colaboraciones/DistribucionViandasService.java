package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.CreateDistribucionViandasDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.heladera.AperturaHeladeraService;
import ar.edu.utn.frba.dds.services.heladera.SolicitudDeAperturaService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaColaboradorService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

/**
 * Servicio de Distribucion de Viandas.
 */
public class DistribucionViandasService implements WithSimplePersistenceUnit {

  private final DistribucionViandasRepository distribucionViandasRepository;
  private final HeladeraRepository heladeraRepository;
  private final TarjetaColaboradorService tarjetaColaboradorService;
  private final SolicitudDeAperturaService solicitudDeAperturaService;
  private final AperturaHeladeraService aperturaHeladeraService;

  /**
   * Constructor de DistribucionViandasService.
   *
   * @param distribucionViandasRepository Repositorio de Distribucion de Viandas
   * @param heladeraRepository            Repositorio de Heladera
   * @param solicitudDeAperturaService    Servicio de Solicitud de Apertura
   */
  public DistribucionViandasService(DistribucionViandasRepository distribucionViandasRepository,
                                    HeladeraRepository heladeraRepository,
                                    TarjetaColaboradorService tarjetaColaboradorService,
                                    SolicitudDeAperturaService solicitudDeAperturaService,
                                    AperturaHeladeraService aperturaHeladeraService) {
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.heladeraRepository = heladeraRepository;
    this.tarjetaColaboradorService = tarjetaColaboradorService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
    this.aperturaHeladeraService = aperturaHeladeraService;
  }

  /**
   * Registra una nueva distribución de viandas.
   *
   * @param colaborador Colaborador
   * @param input       DTO de la distribución de viandas
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   * @throws ResourceNotFoundException  Excepción de recurso no encontrado
   */
  public void registrarNuevaDistribucion(Colaborador colaborador,
                                         CreateDistribucionViandasDTO input)
      throws CantidadDeViandasException, ResourceNotFoundException {

    Heladera heladeraOrigen = heladeraRepository
        .buscarPorNombre(input.getHeladeraOrigen())
        .orElseThrow(ResourceNotFoundException::new);

    Heladera heladeraDestino = heladeraRepository
        .buscarPorNombre(input.getHeladeraDestino())
        .orElseThrow(ResourceNotFoundException::new);

    Integer viandas = input.getCantViandas();

    if (!heladeraDestino.puedeAgregarViandas(viandas)) {
      throw new CantidadDeViandasException();
    }

    MotivoApertura motivo = MotivoApertura.DISTRIBUCION_VIANDAS;
    TarjetaColaborador tarjeta = tarjetaColaboradorService.buscarPorColaborador(colaborador);

    SolicitudDeApertura solicitudHeladeraOrigen = solicitudDeAperturaService
        .generarParaExtraccionDeViandas(tarjeta, heladeraOrigen);

    SolicitudDeApertura solicitudHeladeraDestino = solicitudDeAperturaService
        .generarParaIngresoDeViandas(tarjeta, heladeraDestino, motivo);

    DistribucionViandas distribucionViandas = DistribucionViandas.por(
        colaborador,
        heladeraOrigen,
        heladeraDestino,
        viandas,
        input.getMotivo(),
        solicitudHeladeraOrigen,
        solicitudHeladeraDestino
    );

    beginTransaction();
    this.distribucionViandasRepository.guardar(distribucionViandas);
    commitTransaction();
  }

  /**
   * Busca una distribución de viandas por ID.
   *
   * @param id ID de la distribución de viandas
   * @return DTO de la distribución de viandas
   * @throws ResourceNotFoundException Excepción de recurso no encontrado
   */
  public DistribucionViandasDTO buscarPorId(String id) {
    return DistribucionViandasDTO.fromColaboracion(
        distribucionViandasRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new)
    );
  }

  /**
   * Busca una distribución de viandas por solicitud de apeertura.
   *
   * @param solicitud la {@link SolicitudDeApertura} asociada
   * @throws ResourceNotFoundException Excepción de recurso no encontrado
   */
  public void efectuarAperturaPara(SolicitudDeApertura solicitud)
      throws CantidadDeViandasException, ResourceNotFoundException {
    DistribucionViandas distribucionViandas;

    switch (solicitud.getOperacion()) {
      case RETIRO_VIANDAS -> {
        distribucionViandas = distribucionViandasRepository
            .buscarPorSolicitudDeRetiro(solicitud)
            .orElseThrow(ResourceNotFoundException::new);
        this.manejarRetiroDeViandas(solicitud, distribucionViandas);
      }

      case INGRESO_VIANDAS -> {
        distribucionViandas = distribucionViandasRepository
            .buscarPorSolicitudDeIngreso(solicitud)
            .orElseThrow(ResourceNotFoundException::new);
        this.manejarIngresoDeViandas(solicitud, distribucionViandas);
      }

      default -> throw new ResourceNotFoundException();
    }
  }

  // TODO
  private void manejarRetiroDeViandas(SolicitudDeApertura solicitud,
                                      DistribucionViandas distribucionViandas) {
    beginTransaction();
    aperturaHeladeraService.registrarApertura(solicitud);
    commitTransaction();
  }

  // TODO

  private void manejarIngresoDeViandas(SolicitudDeApertura solicitud,
                                       DistribucionViandas distribucionViandas) {
    beginTransaction();
    aperturaHeladeraService.registrarApertura(solicitud);
    commitTransaction();
  }
}
