package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.usuario.Persona;

public class CalculadorDePuntos {

    public Double calcularPuntos(Persona persona){
        return PuntosDonacionDinero.calcularPuntos(persona)
//                +PuntosHeladerasActivas.calcularPuntos(persona)
                +PuntosDistribucionViandas.calcularPuntos(persona)
                +PuntosDonacionViandas.calcularPuntos(persona)
                +PuntosTarjetasDonadas.calcularPuntos(persona)
                -persona.getPuntosCanjeados();
    }
}
