package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.util.List;

public class PuntosDistribucionViandas {

    private double variable = 1;

    public static Double calcularPuntos(Persona persona){
        List<DistribucionViandas> listaViandasDistribuidas = Colaboracion.obtenerViandasDistribuidas(persona);
        int viandasDistribuidas = listaViandasDistribuidas.size();
        return viandasDistribuidas * variable;
    }
}
