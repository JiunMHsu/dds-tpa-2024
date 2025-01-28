package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.CreateDistribucionViandasDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.distribucionViandas.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
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
                                    SolicitudDeAperturaService solicitudDeAperturaService) {
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.heladeraRepository = heladeraRepository;
    this.tarjetaColaboradorService = tarjetaColaboradorService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
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
        .generarParaExtraccionDeViandas(tarjeta, heladeraOrigen, motivo);

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

  // TODO: Implementar handler de la apertura de la heladera y actualización de la cantidad de viandas
  // colaborador.invalidarPuntos();
  // this.colaboradorService.actualizar(colaborador);
}
