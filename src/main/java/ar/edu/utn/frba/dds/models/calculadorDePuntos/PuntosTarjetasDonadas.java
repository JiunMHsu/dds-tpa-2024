package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.util.List;

public class PuntosTarjetasDonadas {
    private double variable = 2;

    public Double calcularPuntos(Persona persona){
        List<Tarjeta> listaTarjetasRepartidas = Colaboracion.obtenerTarjetasRepartidas(persona);
        int tarjetasRepartidas = listaTarjetasRepartidas.size();
        return tarjetasRepartidas * variable;
    }
}
