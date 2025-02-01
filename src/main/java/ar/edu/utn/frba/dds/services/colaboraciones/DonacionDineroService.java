package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.CreateDonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.DonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.donacionDinero.UpdateDonacionPeriodicaDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Servicio de Donación de Dinero.
 */
public class DonacionDineroService implements WithSimplePersistenceUnit {

  private final DonacionDineroRepository donacionDineroRepository;
  private final IColaboradorRepository colaboradorRepository;

  /**
   * Constructor de DonacionDineroService.
   *
   * @param repository            Repositorio de Donación de Dinero
   * @param colaboradorRepository Repositorio de Colaborador
   */
  public DonacionDineroService(DonacionDineroRepository repository, IColaboradorRepository colaboradorRepository) {
    this.donacionDineroRepository = repository;
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
   * Registra una donación periódica.
   * TODO: Implementar
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
  }

  /**
   * Actualiza una donación periódica.
   * TODO: Implementar
   *
   * @param colaborador       Colaborador que realiza la donación
   * @param donacionPeriodica Datos de la donación periódica
   */
  public void actualizarDonacionPeriodica(Colaborador colaborador,
                                          UpdateDonacionPeriodicaDTO donacionPeriodica) {
  }

  /**
   * Busca una donación periódica por el colaborador.
   * TODO: Implementar
   *
   * @param colaborador Colaborador
   * @return Donación periódica en formato DTO, null si no existe
   */
  public DonacionPeriodicaDTO buscarDonacionPeriodicaPorColaborador(Colaborador colaborador) {
    return null;
  }

}
