package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.*;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.repository.canjeDePuntosRepository.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.RepartoDeTarjetasRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.Builder;

@Builder
public class PuntosPorColaboracion {

  private Colaborador colaborador;
  private LocalDate fechaUltimoCanje;
  private Integer puntosSobrantes;
  private VarianteCalculoDePuntos variante;

  public static PuntosPorColaboracion of(Colaborador colaborador) {
    PuntosPorColaboracionBuilder puntosPorColaboracion = PuntosPorColaboracion
        .builder()
        .colaborador(colaborador)
        .fechaUltimoCanje(LocalDate.now())
        .puntosSobrantes(0)
        .variante(new VarianteCalculoDePuntos());

    CanjeDePuntos ultimoCanjeo = CanjeDePuntosRepository.obtenerUltimoPorColaborador(colaborador);
    if (ultimoCanjeo != null) {
      puntosPorColaboracion.puntosSobrantes(ultimoCanjeo.getPuntosRestantes());
      puntosPorColaboracion.fechaUltimoCanje(ultimoCanjeo.getFechaCanjeo());
    }

    return puntosPorColaboracion.build();
  }

  public Double calcularPuntos() {
    return this.calcularPorPesosDonados()
        + this.calcularPorViandasDistribuidas()
        + this.calcularPorViandasDonadas()
        + this.calcularPorTarjetasRepartidas()
        + this.calcularPorHeladerasActivas()
        + puntosSobrantes;
  }

  private Double calcularPorPesosDonados() {
    List<DonacionDinero> listaDonacionesDinero = DonacionDineroRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double pesosDonados = (double) listaDonacionesDinero.stream()
        .mapToInt(DonacionDinero::getMonto)
        .sum();

    return pesosDonados * variante.getDonacionDinero();
  }

  private Double calcularPorViandasDistribuidas() {
    List<DistribucionViandas> listaViandasDistribuidas = DistribucionViandasRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double viandasDistribuidas = (double) listaViandasDistribuidas.size();
    return viandasDistribuidas * variante.getDistribucionViandas();
  }

  private Double calcularPorViandasDonadas() {
    List<DonacionVianda> listaViandasDonadas = DonacionViandaRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double viandasDonadas = (double) listaViandasDonadas.size();
    return viandasDonadas * variante.getDonacionVianda();
  }

  private Double calcularPorTarjetasRepartidas() {
    List<RepartoDeTarjetas> listaTarjetasRepartidas = RepartoDeTarjetasRepository
        .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

    Double tarjetasRepartidas = (double) listaTarjetasRepartidas.size();

    return tarjetasRepartidas * variante.getRepartoTarjeta();
  }

  private Double calcularPorHeladerasActivas() {
    List<Heladera> listaHeladerasACargo = HacerseCargoHeladeraRepository
        .obtenerPorColaborador(colaborador)
        .stream()
        .map(HacerseCargoHeladera::getHeladeraACargo)
        .toList();

    List<Heladera> listHeladerasActivas = listaHeladerasACargo.stream()
        .filter(Heladera::estaActiva)
        .toList();

    Double heladerasActivas = (double) listHeladerasActivas.size();
    Double mesesActivas = listHeladerasActivas.stream()
        .mapToDouble(heladera -> this.calcularMesesActiva(heladera.getInicioFuncionamiento()))
        .sum();

    return heladerasActivas * mesesActivas * variante.getHeladerasActivas();
  }

  /**
   * Supone que desde la 'fechaInicio' hasta la fecha de llamada
   * siempre estuvo activa.
   */
  private Integer calcularMesesActiva(LocalDate fechaInicio) {
    LocalDate fechaActual = LocalDate.now();
    Period periodo = Period.between(fechaInicio, fechaActual);
    return periodo.getYears() * 12 + periodo.getMonths();
  }
}