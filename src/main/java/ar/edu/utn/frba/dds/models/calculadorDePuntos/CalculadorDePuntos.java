package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;

import java.util.List;

public class CalculadorDePuntos {


  private List<CalculadorDe> calculadores;

  public CalculadorDePuntos(List<CalculadorDe> calculadores) {
    this.calculadores = calculadores;
  }

  public Double calcularPuntos(Colaborador persona) {
    double puntos = calculadores.stream()
        .mapToDouble(calculador -> calculador.calcularPuntos((persona)))
        .sum();
    return puntos - persona.getPuntosCanjeados();
  }
}