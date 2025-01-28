package ar.edu.utn.frba.dds.services.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.PuntosInvalidosException;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjeta;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.VarianteDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de canje de puntos.
 */
public class CanjeDePuntosService implements WithSimplePersistenceUnit {
  private final CanjeDePuntosRepository canjeDePuntosRepository;
  private final DonacionDineroRepository donacionDineroRepository;
  private final DistribucionViandasRepository distribucionViandasRepository;
  private final DonacionViandaRepository donacionViandaRepository;
  private final RepartoDeTarjetaRepository repartoDeTarjetasRepository;
  private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
  private final ColaboradorRepository colaboradorRepository;
  private final VarianteDePuntosRepository varianteDePuntosRepository;

  /**
   * Constructor.
   *
   * @param canjeDePuntosRepository        el repositorio de canjes de puntos
   * @param donacionDineroRepository       el repositorio de donaciones de dinero
   * @param distribucionViandasRepository  el repositorio de distribuciones de viandas
   * @param donacionViandaRepository       el repositorio de donaciones de viandas
   * @param repartoDeTarjetasRepository    el repositorio de repartos de tarjetas
   * @param hacerseCargoHeladeraRepository el repositorio de heladeras activas
   * @param colaboradorRepository          el repositorio de colaboradores
   * @param varianteDePuntosRepository     el repositorio de variantes de puntos
   */
  public CanjeDePuntosService(CanjeDePuntosRepository canjeDePuntosRepository,
                              DonacionDineroRepository donacionDineroRepository,
                              DistribucionViandasRepository distribucionViandasRepository,
                              DonacionViandaRepository donacionViandaRepository,
                              RepartoDeTarjetaRepository repartoDeTarjetasRepository,
                              HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                              ColaboradorRepository colaboradorRepository,
                              VarianteDePuntosRepository varianteDePuntosRepository) {
    this.canjeDePuntosRepository = canjeDePuntosRepository;
    this.donacionDineroRepository = donacionDineroRepository;
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.donacionViandaRepository = donacionViandaRepository;
    this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
    this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.varianteDePuntosRepository = varianteDePuntosRepository;
  }

  /**
   * Obtiene los puntos de un colaborador.
   * Si el colaborador no tiene puntos o están vencidos, se recalculan.
   *
   * @param colaborador el colaborador
   * @return los puntos del colaborador
   */
  public double getPuntosDeColaborador(Colaborador colaborador) {
    try {
      double puntos = colaborador.puntos();
      System.out.println("se obtuvieron los puntos almacenados de " + colaborador.getNombre());
      return puntos;
    } catch (PuntosInvalidosException e) {
      VarianteDePuntos variante = this.varianteDePuntosRepository.buscarUltimo()
          .orElseThrow(() -> new IllegalStateException("No hay variante de puntos configurada"));

      double puntos = this.calcularPuntos(colaborador, variante);
      colaborador.setPuntos(new Puntos(
          puntos,
          true,
          LocalDate.now().plusMonths(1).withDayOfMonth(1)
      ));
      withTransaction(() -> this.colaboradorRepository.actualizar(colaborador));
      return puntos;
    }
  }

  private double calcularPuntos(Colaborador colaborador, VarianteDePuntos variante) {
    LocalDateTime fechaUltimoCanje = null;
    double puntosRestantes = 0;

    Optional<CanjeDePuntos> ultimoCanje = this.canjeDePuntosRepository
        .ultimoPorColaborador(colaborador);

    if (ultimoCanje.isPresent()) {
      fechaUltimoCanje = ultimoCanje.get().getFechaHora();
      puntosRestantes = ultimoCanje.get().getPuntosRestantes();
    }

    return pesosDonados(colaborador, fechaUltimoCanje) * variante.getDonacionDinero()
        + viandasDistribuidas(colaborador, fechaUltimoCanje) * variante.getDistribucionViandas()
        + viandasDonadas(colaborador, fechaUltimoCanje) * variante.getDonacionVianda()
        + tarjetasRepartidas(colaborador, fechaUltimoCanje) * variante.getRepartoTarjeta()
        + heladerasPorMesesActivas(colaborador, fechaUltimoCanje) * variante.getHeladerasActivas()
        + puntosRestantes;
  }

  private double pesosDonados(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
    List<DonacionDinero> colaboraciones = this.donacionDineroRepository
        .buscarPorColaboradorDesde(colaborador, fechaUltimoCanje);

    return colaboraciones.stream().mapToDouble(DonacionDinero::getMonto).sum();
  }

  private double viandasDistribuidas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
    List<DistribucionViandas> colaboraciones = this.distribucionViandasRepository
        .buscarPorColaboradorDesde(colaborador, fechaUltimoCanje);

    return colaboraciones.stream().mapToDouble(DistribucionViandas::getViandas).sum();
  }

  private double viandasDonadas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
    List<DonacionVianda> colaboraciones = this.donacionViandaRepository
        .buscarPorColaboradorDesde(colaborador, fechaUltimoCanje);

    return colaboraciones.size();
  }

  private double tarjetasRepartidas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
    List<RepartoDeTarjeta> colaboraciones = this.repartoDeTarjetasRepository
        .buscarPorColaboradorDesde(colaborador, fechaUltimoCanje);

    return colaboraciones.size();
  }

  private double heladerasPorMesesActivas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
    List<Heladera> heladerasActivasEncargadas = this.hacerseCargoHeladeraRepository
        .buscarPorColaborador(colaborador).stream()
        .map(HacerseCargoHeladera::getHeladera)
        .filter(Heladera::estaActiva).toList();

    int heladerasActivas = heladerasActivasEncargadas.size();
    int mesesActivas = heladerasActivasEncargadas.stream()
        .mapToInt(heladera -> {
          boolean esPrimerCanje = fechaUltimoCanje == null;
          boolean esMasRecienteQueUltimoCanje =
              !esPrimerCanje && fechaUltimoCanje.isBefore(heladera.getInicioFuncionamiento());

          LocalDateTime fechaPartida = esPrimerCanje || esMasRecienteQueUltimoCanje
              ? heladera.getInicioFuncionamiento()
              : fechaUltimoCanje;

          return this.mesesEntre(fechaPartida, LocalDateTime.now());
        }).sum();

    return heladerasActivas * mesesActivas;
  }

  /**
   * Supone que desde la 'fechaInicio' hasta la fecha de llamada
   * siempre estuvo activa.
   */
  private int mesesEntre(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
    return (int) ChronoUnit.MONTHS.between(YearMonth.from(fechaInicio), YearMonth.from(fechaFinal));
  }

  /**
   * Registra un canje de puntos para un colaborador.
   * Invalida los puntos del colaborador y guarda el canje.
   *
   * @param canjeDePuntos el canje a registrar
   */
  public void registrar(CanjeDePuntos canjeDePuntos) {
    canjeDePuntos.getColaborador().invalidarPuntos();

    beginTransaction();
    this.colaboradorRepository.actualizar(canjeDePuntos.getColaborador());
    this.canjeDePuntosRepository.guardar(canjeDePuntos);
    commitTransaction();
  }

  public List<CanjeDePuntos> buscarTodosxColaborador(Colaborador colaborador) {
    return canjeDePuntosRepository.buscarTodosXColaborador(colaborador);
  }
}
