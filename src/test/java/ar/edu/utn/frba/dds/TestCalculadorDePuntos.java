package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.calculadorDePuntos.*;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestCalculadorDePuntos {
    Persona persona = new Persona(new Usuario("grupo22", "2024", "frba"));
    PuntosTarjetasDonadas calculadorTarjetasDonadas = new PuntosTarjetasDonadas();
//    PuntosDonacionViandas calculadorDonacionViandas;
//    PuntosHeladerasActivas calculadorHeladerasActivas;
//    PuntosDonacionDinero calculadorDonacionDinero;
//    PuntosDistribucionViandas calculadorDistribucionViandas;

    @Test
    @DisplayName("Los puntos por repartir tarjetas son la cantidad de tarjetas repartidas multiplicado por 2")
    public void puntosTarjetas() {
        calculadorTarjetasDonadas.setListaTarjetasRepartidas(new ArrayList<Tarjeta>(){{
            add(new Tarjeta());
            add(new Tarjeta());
            add(new Tarjeta());
        }});
        Assertions.assertEquals(calculadorTarjetasDonadas.calcularPuntos(persona), 6.0);
    }
}
