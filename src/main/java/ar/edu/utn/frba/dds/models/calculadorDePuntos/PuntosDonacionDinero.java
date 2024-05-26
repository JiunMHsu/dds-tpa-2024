package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.util.List;

public class PuntosDonacionDinero {
    private Float variable;

    public Float calcularPuntos(Persona persona){
        List<DonacionDinero> listaDonacionesDinero = Colaboracion.obtenerDonacionesDinero(persona);
//        listaDonacionesDinero.stream()
//                             .mapToInt()
    }
}
