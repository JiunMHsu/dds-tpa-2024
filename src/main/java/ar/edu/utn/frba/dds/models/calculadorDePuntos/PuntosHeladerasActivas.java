package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class PuntosHeladerasActivas implements CalculadorDe {
  private static double variable = 5;
  private List<HacerseCargoHeladera> listaHeladerasACargo = new ArrayList<>();

  public static int calcularMesesActiva(LocalDate fechaInicio) {
    LocalDate fechaActual = LocalDate.now();
    Period periodo = Period.between(fechaInicio, fechaActual);
    return periodo.getYears() * 12 + periodo.getMonths();
  }

  public Double calcularPuntos(Colaborador persona) {
    // TODO cuando veamos base de datos
    List<Heladera> listaHeladerasActivas = listaHeladerasACargo.stream()
        .map(HacerseCargoHeladera::getHeladeraACargo)
        .filter(Heladera::estaActiva)
        .collect(Collectors.toList());
    int heladerasActivas = listaHeladerasActivas.size();
    int mesesActivas = listaHeladerasActivas.stream()
        .mapToInt(heladera -> PuntosHeladerasActivas.calcularMesesActiva(heladera.getFechaInicioFuncionamiento()))
        .sum();
    return heladerasActivas * mesesActivas * variable;
  }


}
