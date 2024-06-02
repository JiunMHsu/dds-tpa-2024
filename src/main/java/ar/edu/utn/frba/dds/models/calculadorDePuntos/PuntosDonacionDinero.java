package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
public class PuntosDonacionDinero implements CalculadorDe{
    private static double variable = 0.5;
    private List<DonacionDinero> listaDonacionesDinero = new ArrayList<>();
    public Double calcularPuntos(Persona persona){
        // TODO cuando veamos base de datos
        int pesosDonados = listaDonacionesDinero.stream()
                             .mapToInt(DonacionDinero :: getMonto)
                             .sum();
        return pesosDonados * variable;
    }
}
