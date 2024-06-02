package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
public class PuntosDistribucionViandas implements CalculadorDe{

    private static double variable = 1;
    private List<DistribucionViandas> listaViandasDistribuidas = new ArrayList<>();
    public Double calcularPuntos(Persona persona){

        // TODO cuando veamos base de datos
        int viandasDistribuidas = listaViandasDistribuidas.size();
        return viandasDistribuidas * variable;
    }
}
