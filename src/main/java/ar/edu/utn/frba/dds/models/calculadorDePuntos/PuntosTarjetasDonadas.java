package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class PuntosTarjetasDonadas implements CalculadorDe {
  private static double variable = 2;

  private List<Tarjeta> listaTarjetasRepartidas = new ArrayList<>();

  public Double calcularPuntos(Colaborador persona) {
    // TODO cuando veamos base de datos
    int tarjetasRepartidas = listaTarjetasRepartidas.size();
    return tarjetasRepartidas * variable;
  }
}
