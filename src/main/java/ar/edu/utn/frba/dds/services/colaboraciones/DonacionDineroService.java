package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.CreateDonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.UpdateDonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDineroPeriodica;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroPeriodicaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.reactivex.annotations.Nullable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

/**
 * Servicio de Donación de Dinero.
 */
public class DonacionDineroService implements WithSimplePersistenceUnit {

  private final DonacionDineroRepository donacionDineroRepository;
  private final DonacionDineroPeriodicaRepository donacionDineroPeriodicaRepository;
  private final IColaboradorRepository colaboradorRepository;

  /**
   * Constructor de DonacionDineroService.
   *
   * @param repository            Repositorio de Donación de Dinero
   * @param colaboradorRepository Repositorio de Colaborador
   */
  public DonacionDineroService(DonacionDineroRepository repository,
                               DonacionDineroPeriodicaRepository donacionDineroPeriodicaRepository,
                               IColaboradorRepository colaboradorRepository) {
    this.donacionDineroRepository = repository;
    this.donacionDineroPeriodicaRepository = donacionDineroPeriodicaRepository;
    this.colaboradorRepository = colaboradorRepository;
  }

  /**
   * Registra una nueva donación de dinero.
   *
   * @param colaborador Colaborador que realiza la donación
   * @param monto       Monto de la donación
   */
  public void registrarDonacion(Colaborador colaborador, int monto) {
    DonacionDinero donacion = DonacionDinero.por(
        colaborador,
        LocalDateTime.now(),
        monto
    );

    colaborador.invalidarPuntos();

    beginTransaction();
    donacionDineroRepository.guardar(donacion);
    colaboradorRepository.actualizar(colaborador);
    commitTransaction();
  }

  /**
   * Busca una donación de dinero por su ID.
   *
   * @param id Id de la donación
   * @return Donación de dinero
   * @throws ResourceNotFoundException si no se encuentra la donación
   */
  public DonacionDineroDTO buscarPorId(String id) {
    return DonacionDineroDTO.fromColaboracion(
        donacionDineroRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new)
    );
  }

  /**
   * Busca una donación periódica por su ID.
   *
   * @param id Id de la donación periódica
   * @return Donación periódica en formato DTO
   */
  public DonacionPeriodicaDTO buscarDonacionPeriodicaPorId(String id) {
    return DonacionPeriodicaDTO.fromDonacionPeriodica(
        donacionDineroPeriodicaRepository.buscarPorId(id)
            .orElseThrow(ResourceNotFoundException::new)
    );
  }

  /**
   * Registra una donación periódica.
   *
   * @param colaborador       Colaborador que realiza la donación
   * @param donacionPeriodica Datos de la donación periódica
   */
  public void registrarDonacionPeriodica(Colaborador colaborador,
                                         CreateDonacionPeriodicaDTO donacionPeriodica) {

    Period frecuencia = switch (donacionPeriodica.getUnidadPeriodicidad()) {
      case "DAY" -> Period.ofDays(donacionPeriodica.getPeriodicidad());
      case "WEEK" -> Period.ofWeeks(donacionPeriodica.getPeriodicidad());
      case "MONTH" -> Period.ofMonths(donacionPeriodica.getPeriodicidad());
      case "YEAR" -> Period.ofYears(donacionPeriodica.getPeriodicidad());
      default -> throw new InvalidFormParamException();
    };

    DonacionDineroPeriodica donacion = DonacionDineroPeriodica.por(
        colaborador,
        donacionPeriodica.getMonto(),
        frecuencia
    );

    beginTransaction();
    donacionDineroPeriodicaRepository.guardar(donacion);
    commitTransaction();
  }

  /**
   * Actualiza una donación periódica.
   *
   * @param colaborador       Colaborador que realiza la donación
   * @param donacionPeriodica Datos de la donación periódica
   * @throws UnauthorizedException si la donación a actualizar no corresponde al colaborador
   */
  public void actualizarDonacionPeriodica(Colaborador colaborador,
                                          UpdateDonacionPeriodicaDTO donacionPeriodica)
      throws UnauthorizedException, ResourceNotFoundException {
    DonacionDineroPeriodica donacion = donacionDineroPeriodicaRepository
        .buscarPorId(donacionPeriodica.getId())
        .orElseThrow(ResourceNotFoundException::new);

    if (!donacion.getColaborador().equals(colaborador)) {
      throw new UnauthorizedException();
    }

    Period frecuencia = switch (donacionPeriodica.getUnidadPeriodicidad()) {
      case "DAY" -> Period.ofDays(donacionPeriodica.getPeriodicidad());
      case "WEEK" -> Period.ofWeeks(donacionPeriodica.getPeriodicidad());
      case "MONTH" -> Period.ofMonths(donacionPeriodica.getPeriodicidad());
      case "YEAR" -> Period.ofYears(donacionPeriodica.getPeriodicidad());
      default -> throw new InvalidFormParamException();
    };

    donacion.setMonto(donacionPeriodica.getMonto());
    donacion.setFrecuencia(frecuencia);

    beginTransaction();
    this.donacionDineroPeriodicaRepository.actualizar(donacion);
    commitTransaction();
  }


  /**
   * Busca una donación periódica por colaborador.
   *
   * @param colaborador Colaborador
   * @return Donación periódica en formato DTO
   */
  public DonacionPeriodicaDTO buscarDonacionPeriodica(Colaborador colaborador)
      throws ResourceNotFoundException {
    return this.buscarDonacionPeriodica(colaborador, null);
  }

  /**
   * Busca una donación periódica.
   *
   * @param colaborador Colaborador
   * @param id          Id de la donación periódica
   * @return Donación periódica en formato DTO
   */
  public DonacionPeriodicaDTO buscarDonacionPeriodica(Colaborador colaborador, @Nullable String id)
      throws UnauthorizedException, ResourceNotFoundException {

    DonacionDineroPeriodica donacion = donacionDineroPeriodicaRepository
        .buscarPorColaborador(colaborador)
        .orElseThrow(ResourceNotFoundException::new);

    if (id != null && !UUID.fromString(id).equals(donacion.getId())) {
      throw new UnauthorizedException();
    }

    return DonacionPeriodicaDTO.fromDonacionPeriodica(donacion);
  }

}
