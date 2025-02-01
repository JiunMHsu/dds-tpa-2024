package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.CreateDistribucionViandasDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.EstadoDistribucion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
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
  private final ColaboradorRepository colaboradorRepository;
  private final HeladeraRepository heladeraRepository;
  private final TarjetaColaboradorService tarjetaColaboradorService;
  private final SolicitudDeAperturaService solicitudDeAperturaService;
  private final AperturaHeladeraService aperturaHeladeraService;

  /**
   * Constructor de DistribucionViandasService.
   *
   * @param distribucionViandasRepository Repositorio de Distribucion de Viandas
   * @param colaboradorRepository         Repositorio de Colaborador
   * @param heladeraRepository            Repositorio de Heladera
   * @param tarjetaColaboradorService     Servicio de Tarjeta de Colaborador
   * @param solicitudDeAperturaService    Servicio de Solicitud de Apertura
   * @param aperturaHeladeraService       Servicio de Apertura de Heladera
   */
  public DistribucionViandasService(DistribucionViandasRepository distribucionViandasRepository,
                                    ColaboradorRepository colaboradorRepository,
                                    HeladeraRepository heladeraRepository,
                                    TarjetaColaboradorService tarjetaColaboradorService,
                                    SolicitudDeAperturaService solicitudDeAperturaService,
                                    AperturaHeladeraService aperturaHeladeraService) {
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.heladeraRepository = heladeraRepository;
    this.tarjetaColaboradorService = tarjetaColaboradorService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
    this.aperturaHeladeraService = aperturaHeladeraService;
  }

  /**
   * Registra una nueva distribución de viandas.
   *
   * @param colaborador       Colaborador
   * @param nuevaDistribucion DTO de la distribución de viandas
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   * @throws ResourceNotFoundException  Excepción de recurso no encontrado
   */
  public void registrarNuevaDistribucion(Colaborador colaborador,
                                         CreateDistribucionViandasDTO nuevaDistribucion)
      throws CantidadDeViandasException, ResourceNotFoundException {

    Heladera heladeraOrigen = heladeraRepository
        .buscarPorNombre(nuevaDistribucion.getHeladeraOrigen())
        .orElseThrow(ResourceNotFoundException::new);

    Heladera heladeraDestino = heladeraRepository
        .buscarPorNombre(nuevaDistribucion.getHeladeraDestino())
        .orElseThrow(ResourceNotFoundException::new);

    Integer viandas = nuevaDistribucion.getCantViandas();

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
        nuevaDistribucion.getMotivo(),
        solicitudHeladeraOrigen,
        solicitudHeladeraDestino
    );

    beginTransaction();
    distribucionViandasRepository.guardar(distribucionViandas);
    commitTransaction();
  }

  /**
   * Busca una distribución de viandas por ID.
   * Sirve para el show, borrar si no se va a implementar.
   *
   * @param id Id de la distribución de viandas
   * @return DTO de la distribución de viandas
   * @throws ResourceNotFoundException Excepción de recurso no encontrado
   */
  public DistribucionViandasDTO buscarPorId(String id) {
    return DistribucionViandasDTO.fromColaboracion(
        distribucionViandasRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new)
    );
  }

  /**
   * Efectúa la apertura de una heladera para una distribución de viandas.
   *
   * @param solicitud la {@link SolicitudDeApertura} asociada
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   * @throws ResourceNotFoundException  Excepción de recurso no encontrado
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

  /**
   * Maneja el retiro de viandas.
   * Retirar viandas en una distribución de viandas implica que se abrió la heladera origen,
   * y la distribución entra en proceso.
   *
   * @param solicitud           la {@link SolicitudDeApertura} asociada
   * @param distribucionViandas la {@link DistribucionViandas} asociada
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   */
  private void manejarRetiroDeViandas(SolicitudDeApertura solicitud,
                                      DistribucionViandas distribucionViandas)
      throws CantidadDeViandasException {
    Heladera heladera = distribucionViandas.getOrigen();
    heladera.quitarViandas(distribucionViandas.getViandas());

    distribucionViandas.setEstado(EstadoDistribucion.INICIADA);

    beginTransaction();
    heladeraRepository.actualizar(heladera);
    distribucionViandasRepository.actualizar(distribucionViandas);
    solicitudDeAperturaService.completarSolicitud(solicitud);
    aperturaHeladeraService.registrarApertura(solicitud);
    commitTransaction();
  }

  /**
   * Maneja el ingreso de viandas.
   * Ingresar viandas en una distribución de viandas implica que se abrió la heladera destino,
   * y la distribución se fromPersonaVulnerable.
   *
   * @param solicitud           la {@link SolicitudDeApertura} asociada
   * @param distribucionViandas la {@link DistribucionViandas} asociada
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   */
  private void manejarIngresoDeViandas(SolicitudDeApertura solicitud,
                                       DistribucionViandas distribucionViandas)
      throws CantidadDeViandasException {
    Heladera heladera = distribucionViandas.getDestino();
    heladera.agregarViandas(distribucionViandas.getViandas());

    distribucionViandas.setEstado(EstadoDistribucion.COMPLETADA);
    distribucionViandas.getColaborador().invalidarPuntos();

    beginTransaction();
    heladeraRepository.actualizar(heladera);
    distribucionViandasRepository.actualizar(distribucionViandas);
    colaboradorRepository.actualizar(distribucionViandas.getColaborador());
    solicitudDeAperturaService.completarSolicitud(solicitud);
    aperturaHeladeraService.registrarApertura(solicitud);
    commitTransaction();
  }
}
