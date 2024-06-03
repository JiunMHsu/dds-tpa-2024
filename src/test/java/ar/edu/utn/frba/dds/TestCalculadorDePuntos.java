package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.calculadorDePuntos.*;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
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
    PuntosDonacionDinero calculadorDonacionDinero = new PuntosDonacionDinero();
    PuntosDonacionViandas calculadorDonacionViandas= new PuntosDonacionViandas();
    PuntosHeladerasActivas calculadorHeladerasActivas = new PuntosHeladerasActivas();
    PuntosDistribucionViandas calculadorDistribucionViandas = new PuntosDistribucionViandas();

    @Test
    @DisplayName("Los puntos por repartir tarjetas son la cantidad de tarjetas repartidas multiplicado por 2")
    public void puntosTarjetas() {
        calculadorTarjetasDonadas.setListaTarjetasRepartidas(new ArrayList<Tarjeta>(){{
            add(new Tarjeta(null, null, null, null, null, null));
            add(new Tarjeta(null, null, null, null, null, null));
            add(new Tarjeta(null, null, null, null, null, null));
        }});
        Assertions.assertEquals(calculadorTarjetasDonadas.calcularPuntos(persona), 6.0);
    }
    @Test
    @DisplayName("Los puntos por donar dinero son la cantidad de dinero donado multiplicado por 0.5")
    public void puntosDonacionDinero() {
        calculadorDonacionDinero.setListaDonacionesDinero(new ArrayList<DonacionDinero>(){{
            add(new DonacionDinero(persona, 500));
            add(new DonacionDinero(persona, 200));
            add(new DonacionDinero(persona, 10000));
        }});
        Assertions.assertEquals(calculadorDonacionDinero.calcularPuntos(persona), 5350.0);
    }

    @Test
    @DisplayName("Los puntos obtenidos de una persona es la sumatoria de los puntos por cada forma colaborada restado a sus puntos canjeados")
    public void puntosObtenidos() {
        calculadorDonacionDinero.setListaDonacionesDinero(new ArrayList<DonacionDinero>(){{
            add(new DonacionDinero(persona, 500));
            add(new DonacionDinero(persona, 200));
            add(new DonacionDinero(persona, 10000));
        }});
        calculadorTarjetasDonadas.setListaTarjetasRepartidas(new ArrayList<Tarjeta>(){{
            add(new Tarjeta(null, null, null, null, null, null));
            add(new Tarjeta(null, null, null, null, null, null));
            add(new Tarjeta(null, null, null, null, null, null));
        }});

        CalculadorDePuntos calculadorDePuntosObtenidos = new CalculadorDePuntos(new ArrayList<CalculadorDe>(){{
            add(calculadorDonacionDinero);
            add(calculadorTarjetasDonadas);
        }});

        persona.setPuntosCanjeados(1000.0);
        Assertions.assertEquals(calculadorDePuntosObtenidos.calcularPuntos(persona), 4356.0);
    }
}
