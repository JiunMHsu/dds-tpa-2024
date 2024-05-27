package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.util.List;

public class PuntosDonacionDinero {
    private static double variable = 0.5;

    public static Double calcularPuntos(Persona persona){
        List<DonacionDinero> listaDonacionesDinero = Colaboracion.obtenerDonacionesDinero(persona);
        int pesosDonados = listaDonacionesDinero.stream()
                             .mapToInt(DonacionDinero :: getMonto)
                             .sum();
        return pesosDonados * variable;
    }
}
