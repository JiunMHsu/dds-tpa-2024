package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.*;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

/**
 * Todas las listas de colaboraciones usadas para los cálculos
 * están inicializadas como lista vacía, los cuales deberían ser reemplazadas
 * al momento de implementar persistencia.
 * Esos datos se obtendrán por consulta a la DB.
 */
@Builder
public class PuntosPorColaboracion {

  private Colaborador colaborador;
  private LocalDate fechaUltimoCanje;
  private VarianteCalculoDePuntos variante;

  public static PuntosPorColaboracion of(Colaborador colaborador) {
    LocalDate ultimoCanje = LocalDate.now(); // Se debería hacer una búsqueda
    return PuntosPorColaboracion
        .builder()
        .colaborador(colaborador)
        .fechaUltimoCanje(ultimoCanje)
        .variante(new VarianteCalculoDePuntos())
        .build();
  }

  public Double calcularPuntos() {
    return this.calcularPorPesosDonados()
        + this.calcularPorViandasDistribuidas()
        + this.calcularPorViandasDonadas()
        + this.calcularPorTarjetasRepartidas()
        + this.calcularPorHeladerasActivas();
  }

  private Double calcularPorPesosDonados() {
    List<DonacionDinero> listaDonacionesDinero = new ArrayList<>();
    Double pesosDonados = (double) listaDonacionesDinero.stream()
        .mapToInt(DonacionDinero::getMonto)
        .sum();

    return pesosDonados * variante.getDonacionDinero();
  }

  private Double calcularPorViandasDistribuidas() {
    List<DistribucionViandas> listaViandasDistribuidas = new ArrayList<>();
    Double viandasDistribuidas = (double) listaViandasDistribuidas.size();

    return viandasDistribuidas * variante.getDistribucionViandas();
  }

  private Double calcularPorViandasDonadas() {
    List<DonacionVianda> listaViandasDonadas = new ArrayList<>();
    Double viandasDonadas = (double) listaViandasDonadas.size();

    return viandasDonadas * variante.getDonacionVianda();
  }

  private Double calcularPorTarjetasRepartidas() {
    List<RepartoDeTarjetas> listaTarjetasRepartidas = new ArrayList<>();
    Double tarjetasRepartidas = (double) listaTarjetasRepartidas.size();

    return tarjetasRepartidas * variante.getRepartoTarjeta();
  }

  private Double calcularPorHeladerasActivas() {
    List<Heladera> listaHeladerasACargo = new ArrayList<>();
    List<Heladera> listHeladerasActivas = listaHeladerasACargo.stream()
        .filter(Heladera::estaActiva)
        .toList();

    Double heladerasActivas = (double) listHeladerasActivas.size();
    Double mesesActivas = listHeladerasActivas.stream()
        .mapToDouble(heladera -> this.calcularMesesActiva(heladera.getInicioFuncionamiento()))
        .sum();

    return heladerasActivas * mesesActivas * variante.getHeladerasActivas();
  }

  private Integer calcularMesesActiva(LocalDate fechaInicio) {
    LocalDate fechaActual = LocalDate.now();
    Period periodo = Period.between(fechaInicio, fechaActual);
    return periodo.getYears() * 12 + periodo.getMonths();
  }
}