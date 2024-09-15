package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.repository.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.RepartoDeTarjetasRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public class PuntosPorColaboracion {

  private Colaborador colaborador;
  private LocalDateTime fechaUltimoCanje;
  private Double puntosSobrantes;
  private VarianteCalculoDePuntos variante;
  private CanjeDePuntosRepository canjeDePuntosRepository;
  private DistribucionViandasRepository distribucionViandasRepository;
  private DonacionDineroRepository donacionDineroRepository;
  private DonacionViandaRepository donacionViandaRepository;
  private RepartoDeTarjetasRepository repartoDeTarjetasRepository;
  private HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

  public PuntosPorColaboracion of(Colaborador colaborador) {
    PuntosPorColaboracionBuilder puntosPorColaboracion = PuntosPorColaboracion
        .builder()
        .colaborador(colaborador)
        .fechaUltimoCanje(null)
        .puntosSobrantes(0.0)
        .variante(new VarianteCalculoDePuntos())
        .canjeDePuntosRepository(new CanjeDePuntosRepository())
        .distribucionViandasRepository(new DistribucionViandasRepository())
        .donacionDineroRepository(new DonacionDineroRepository())
        .donacionViandaRepository(new DonacionViandaRepository())
        .repartoDeTarjetasRepository(new RepartoDeTarjetasRepository())
        .hacerseCargoHeladeraRepository(new HacerseCargoHeladeraRepository());



    CanjeDePuntos ultimoCanjeo = canjeDePuntosRepository.obtenerUltimoPorColaborador(colaborador);
    if (ultimoCanjeo != null) {
      puntosPorColaboracion.puntosSobrantes(ultimoCanjeo.getPuntosRestantes());
      puntosPorColaboracion.fechaUltimoCanje(ultimoCanjeo.getFechaCanjeo());
    }
    return puntosPorColaboracion.build();
  }

  public Double calcularPuntos() {
    System.out.println(puntosSobrantes);
    System.out.println(fechaUltimoCanje);
    return this.calcularPorPesosDonados()
        + this.calcularPorViandasDistribuidas()
        + this.calcularPorViandasDonadas()
        + this.calcularPorTarjetasRepartidas()
        + this.calcularPorHeladerasActivas()
        + puntosSobrantes;
  }

  private Double calcularPorPesosDonados() {
    List<DonacionDinero> listaDonacionesDinero = donacionDineroRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double pesosDonados = listaDonacionesDinero.stream()
        .mapToDouble(DonacionDinero::getMonto)
        .sum();

    Double puntaje = pesosDonados * variante.getDonacionDinero();
    System.out.println("donacion dinero");
    System.out.println(puntaje);
    return puntaje;
  }

  private Double calcularPorViandasDistribuidas() {
    List<DistribucionViandas> listaViandasDistribuidas = distribucionViandasRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double viandasDistribuidas = listaViandasDistribuidas.stream()
        .mapToDouble(DistribucionViandas::getViandas)
        .sum();

    Double puntaje = viandasDistribuidas * variante.getDistribucionViandas();
    System.out.println("viandas distribuidas");
    System.out.println(puntaje);
    return puntaje;
  }

  private Double calcularPorViandasDonadas() {
    List<DonacionVianda> listaViandasDonadas = donacionViandaRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double viandasDonadas = (double) listaViandasDonadas.size();

    Double puntaje = viandasDonadas * variante.getDonacionVianda();
    System.out.println("viandas donadas");
    System.out.println(puntaje);
    return puntaje;
  }

  private Double calcularPorTarjetasRepartidas() {
    List<RepartoDeTarjetas> listaTarjetasRepartidas = repartoDeTarjetasRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double tarjetasRepartidas = (double) listaTarjetasRepartidas.size();

    Double puntaje = tarjetasRepartidas * variante.getRepartoTarjeta();
    System.out.println("tarjeras repartidas");
    System.out.println(puntaje);
    return puntaje;
  }

  private Double calcularPorHeladerasActivas() {
    List<Heladera> listaHeladerasACargo = hacerseCargoHeladeraRepository
        .obtenerPorColaborador(colaborador)
        .stream()
        .map(HacerseCargoHeladera::getHeladeraACargo)
        .toList();

    List<Heladera> listHeladerasActivas = listaHeladerasACargo.stream()
        .filter(Heladera::estaActiva)
        .toList();

    Double heladerasActivas = (double) listHeladerasActivas.size();
    Double mesesActivas = listHeladerasActivas.stream()
        .mapToDouble(heladera -> this.calcularMesesActiva(heladera.getInicioFuncionamiento(), LocalDate.now()))
        .sum();

    Double puntajeTotalActual = heladerasActivas * mesesActivas * variante.getHeladerasActivas();
    Double puntajeAnterior;
    if (fechaUltimoCanje == null) {
      puntajeAnterior = 0.0;
    } else {
      puntajeAnterior = this.calcularPorHeladederasAnteriorCanje(listHeladerasActivas);
    }

    Double puntaje = puntajeTotalActual - puntajeAnterior;
    System.out.println("hacerse cargo heladera");
    System.out.println(puntaje);
    System.out.println(puntajeTotalActual);
    return puntaje;
  }

  /**
   * Supone que desde la 'fechaInicio' hasta la fecha de llamada
   * siempre estuvo activa.
   */
  private Integer calcularMesesActiva(LocalDateTime fechaInicio, LocalDateTime fechaActual) {
    Period periodo = Period.between(fechaInicio.toLocalDate(), fechaActual.toLocalDate());
    return periodo.getYears() * 12 + periodo.getMonths();
  }

  private Double calcularPorHeladederasAnteriorCanje(List<Heladera> listHeladerasActivas) {
    List<Heladera> heladerasActivasAntesDelUltimoCanje = listHeladerasActivas.stream()
        .filter(heladera -> heladera.getInicioFuncionamiento().isBefore(ChronoLocalDate.from(fechaUltimoCanje)))
        .toList();
    Double heladerasActivas = (double) heladerasActivasAntesDelUltimoCanje.size();
    Double mesesActivas = heladerasActivasAntesDelUltimoCanje.stream()
        .mapToDouble(heladera -> this.calcularMesesActiva(heladera.getInicioFuncionamiento(), fechaUltimoCanje))
        .sum();

    Double puntaje = heladerasActivas * mesesActivas * variante.getHeladerasActivas();
    System.out.println("heladera anterior puntaje");
    System.out.println(puntaje);
    return puntaje;
  }
}