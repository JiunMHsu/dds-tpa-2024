package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionVianda.CreateDonacionViandaDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.MotivoApertura;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Comida;
import ar.edu.utn.frba.dds.models.entities.heladera.CantidadDeViandasException;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.vianda.Vianda;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.vianda.ViandaRepository;
import ar.edu.utn.frba.dds.services.heladera.AperturaHeladeraService;
import ar.edu.utn.frba.dds.services.heladera.SolicitudDeAperturaService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaColaboradorService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;

/**
 * Servicio de Donación de Viandas.
 */
public class DonacionViandaService implements WithSimplePersistenceUnit {

  private final DonacionViandaRepository donacionViandaRepository;
  private final ViandaRepository viandaRepository;
  private final IColaboradorRepository colaboradorRepository;
  private final HeladeraRepository heladeraRepository;
  private final TarjetaColaboradorService tarjetaColaboradorService;
  private final SolicitudDeAperturaService solicitudDeAperturaService;
  private final AperturaHeladeraService aperturaHeladeraService;

  /**
   * Constructor de DonacionViandaService.
   *
   * @param donacionViandaRepository   Repositorio de Donación de Viandas
   * @param viandaRepository           Repositorio de Vianda
   * @param colaboradorRepository      Repositorio de Colaborador
   * @param heladeraRepository         Repositorio de Heladera
   * @param tarjetaColaboradorService  Servicio de Tarjeta de Colaborador
   * @param solicitudDeAperturaService Servicio de Solicitud de Apertura
   * @param aperturaHeladeraService    Servicio de Apertura de Heladera
   */
  public DonacionViandaService(DonacionViandaRepository donacionViandaRepository,
                               ViandaRepository viandaRepository,
                               IColaboradorRepository colaboradorRepository,
                               HeladeraRepository heladeraRepository,
                               TarjetaColaboradorService tarjetaColaboradorService,
                               SolicitudDeAperturaService solicitudDeAperturaService,
                               AperturaHeladeraService aperturaHeladeraService) {
    this.donacionViandaRepository = donacionViandaRepository;
    this.viandaRepository = viandaRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.heladeraRepository = heladeraRepository;
    this.tarjetaColaboradorService = tarjetaColaboradorService;
    this.solicitudDeAperturaService = solicitudDeAperturaService;
    this.aperturaHeladeraService = aperturaHeladeraService;
  }

  /**
   * Registra una donación de vianda.
   *
   * @param colaborador   Colaborador que dona la vianda
   * @param nuevaDonacion Datos de la donación
   */
  public void registrar(Colaborador colaborador, CreateDonacionViandaDTO nuevaDonacion)
      throws CantidadDeViandasException, ResourceNotFoundException {

    Heladera heladera = heladeraRepository
        .buscarPorNombre(nuevaDonacion.getHeladera())
        .orElseThrow(ResourceNotFoundException::new);

    if (!heladera.puedeAgregarViandas(1)) {
      throw new CantidadDeViandasException();
    }

    Vianda vianda = Vianda.con(
        new Comida(nuevaDonacion.getNombreComida(), nuevaDonacion.getCaloriasComida()),
        nuevaDonacion.getFechaCaducidadComida(),
        nuevaDonacion.getPesoVianda()
    );

    MotivoApertura motivo = MotivoApertura.DONACION_VIANDA;
    TarjetaColaborador tarjeta = tarjetaColaboradorService.buscarPorColaborador(colaborador);

    SolicitudDeApertura solicitud = solicitudDeAperturaService
        .generarParaIngresoDeViandas(tarjeta, heladera, motivo);

    DonacionVianda donacionVianda = DonacionVianda
        .por(colaborador, LocalDateTime.now(), vianda, heladera, solicitud);

    beginTransaction();
    viandaRepository.guardar(donacionVianda.getVianda());
    donacionViandaRepository.guardar(donacionVianda);
    commitTransaction();
  }

  /**
   * Busca una donación de vianda por su id.
   * Sirve para el show, borrar si no se va a implementar.
   * TODO: Mapear a DTO
   *
   * @param id Id de la donación de vianda
   * @return Donación de vianda
   * @throws ResourceNotFoundException Excepción de recurso no encontrado
   */
  public DonacionVianda buscarPorId(String id) {
    return donacionViandaRepository
        .buscarPorId(id)
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Efectúa la apertura de una heladera para una donación de vianda.
   *
   * @param solicitud Solicitud de apertura
   * @throws CantidadDeViandasException Excepción de cantidad de viandas
   * @throws ResourceNotFoundException  Excepción de recurso no encontrado
   */
  public void efectuarAperturaPara(SolicitudDeApertura solicitud)
      throws CantidadDeViandasException, ResourceNotFoundException {
    DonacionVianda donacion = donacionViandaRepository
        .buscarPorSolicitudDeApertura(solicitud)
        .orElseThrow(ResourceNotFoundException::new);

    Heladera heladera = donacion.getHeladera();
    heladera.agregarViandas(1);

    donacion.getColaborador().invalidarPuntos();

    beginTransaction();
    heladeraRepository.actualizar(heladera);
    donacionViandaRepository.actualizar(donacion);
    colaboradorRepository.actualizar(donacion.getColaborador());
    solicitudDeAperturaService.completarSolicitud(solicitud);
    aperturaHeladeraService.registrarApertura(solicitud);
    commitTransaction();
  }
}
