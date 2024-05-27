package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.util.List;

public class PuntosDonacionViandas {
    private double variable = 1.5;

    public Double calcularPuntos(Persona persona){
        List<DonacionVianda> listaViandasDonadas = Colaboracion.obtenerViandasDonadas(persona);
        int viandasDonadas = listaViandasDonadas.size();
        return viandasDonadas * variable;
    }
}
