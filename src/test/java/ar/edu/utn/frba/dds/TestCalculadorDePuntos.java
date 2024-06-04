package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.calculadorDePuntos.*;
import ar.edu.utn.frba.dds.models.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.data.Comida;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TestCalculadorDePuntos {
    Persona persona = new Persona(new Usuario("grupo22", "2024", "frba"));
    Vianda vianda1 = new Vianda( new Comida( "arroz", 400), null, 23);
    Vianda vianda2 = new Vianda( new Comida( "pollo", 520), null, 24);
    Vianda vianda3 = new Vianda( new Comida( "carne", 600), null, 27);
    Vianda vianda4 = new Vianda( new Comida( "sopa", 250), null, 20);
    Vianda vianda5 = new Vianda( new Comida( "asado", 500), null, 26);

    List<Vianda> listaViandas1 = new ArrayList<>(){{
        add(vianda1);
        add(vianda2);
    }};
    List<Vianda> listaViandas2 = new ArrayList<>(){{
        add(vianda3);
    }};
    List<Vianda> listaViandas3 = new ArrayList<>(){{
        add(vianda4);
        add(vianda5);
        add(vianda3);
    }};
    Heladera heladera1 = new Heladera(null,null, LocalDate.of(2024, 6, 1),null, listaViandas1, null, true);
    Heladera heladera2 = new Heladera(null,null,LocalDate.of(2024, 6, 3),null, listaViandas2, null, false);

    PuntosTarjetasDonadas calculadorTarjetasDonadas = new PuntosTarjetasDonadas();
    PuntosDonacionDinero calculadorDonacionDinero = new PuntosDonacionDinero();
    PuntosDonacionViandas calculadorDonacionViandas= new PuntosDonacionViandas();
    PuntosHeladerasActivas calculadorHeladerasActivas = new PuntosHeladerasActivas();
    PuntosDistribucionViandas calculadorDistribucionViandas = new PuntosDistribucionViandas();

    @Test
    @DisplayName("Los puntos por repartir tarjetas es la cantidad de tarjetas repartidas multiplicado por 2")
    public void puntosTarjetas() {
        calculadorTarjetasDonadas.setListaTarjetasRepartidas(new ArrayList<Tarjeta>(){{
            add(new Tarjeta(null, null, null, null, null, null));
            add(new Tarjeta(null, null, null, null, null, null));
            add(new Tarjeta(null, null, null, null, null, null));
        }});
        Assertions.assertEquals(calculadorTarjetasDonadas.calcularPuntos(persona), 6.0);
    }
    @Test
    @DisplayName("Los puntos por donar dinero es la cantidad de dinero donado multiplicado por 0.5")
    public void puntosDonacionDinero() {
        calculadorDonacionDinero.setListaDonacionesDinero(new ArrayList<DonacionDinero>(){{
            add(new DonacionDinero(persona, 500));
            add(new DonacionDinero(persona, 200));
            add(new DonacionDinero(persona, 10000));
        }});
        Assertions.assertEquals(calculadorDonacionDinero.calcularPuntos(persona), 5350.0);
    }
    @Test
    @DisplayName("Los puntos por distribuir viandas es la cantidad de viandas distribuidas multiplicado por 1")
    public void puntosDistribucionViandas() {
        calculadorDistribucionViandas.setListaViandasDistribuidas(new ArrayList<DistribucionViandas>(){{
            add(new DistribucionViandas(null, null, listaViandas1 , persona));
            add(new DistribucionViandas(null, null, listaViandas2 , persona));
            add(new DistribucionViandas(null, null, listaViandas3 , persona));
        }});
        Assertions.assertEquals(calculadorDistribucionViandas.calcularPuntos(persona), 3.0);
    }
    @Test
    @DisplayName("Los puntos por distribuir viandas es la cantidad de viandas distribuidas multiplicado por 1")
    public void puntosHeladerasActivas() {
        calculadorHeladerasActivas.setListaHeladerasACargo(new ArrayList<HacerseCargoHeladera>(){{
            add(new HacerseCargoHeladera(persona, heladera1));
            add(new HacerseCargoHeladera(persona, heladera2));
        }});
        Assertions.assertEquals(calculadorHeladerasActivas.calcularPuntos(persona), 0.0);
    }
    @Test
    @DisplayName("Los puntos por donar viandas es la cantidad de viandas donadas multiplicado por 1.5")
    public void puntosDonacionViandas() {
        calculadorDonacionViandas.setListaViandasDonadas(new ArrayList<DonacionVianda>(){{
            add(new DonacionVianda(persona, vianda1, heladera1 ));
            add(new DonacionVianda(persona, vianda2, heladera2));
        }});
        Assertions.assertEquals(calculadorDonacionViandas.calcularPuntos(persona), 3.0);
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
//        calculadorDistribucionViandas.setListaViandasDistribuidas(new ArrayList<DistribucionViandas>(){{
//            add(new DistribucionViandas(null, null, listaViandas1 , persona));
//            add(new DistribucionViandas(null, null, listaViandas2 , persona));
//            add(new DistribucionViandas(null, null, listaViandas3 , persona));
//        }});
//        calculadorHeladerasActivas.setListaHeladerasACargo(new ArrayList<HacerseCargoHeladera>(){{
//            add(new HacerseCargoHeladera(persona, new Heladera(null, null, null, null, null, null, true )));
//            add(new HacerseCargoHeladera(persona, new Heladera(null, null, null, null, null, null, false )));
//        }});
//        calculadorDonacionViandas.setListaViandasDonadas(new ArrayList<DonacionVianda>(){{
//            add(new DonacionVianda(persona, vianda1,heladera1 ));
//            add(new DonacionVianda(persona, vianda2,heladera2 ));
//        }});
        CalculadorDePuntos calculadorDePuntosObtenidos = new CalculadorDePuntos(new ArrayList<CalculadorDe>(){{
            add(calculadorDonacionDinero);
            add(calculadorTarjetasDonadas);
//            add(calculadorDistribucionViandas);
//            add(calculadorHeladerasActivas);
//            add(calculadorDonacionViandas);
        }});

        persona.setPuntosCanjeados(1000.0);
        Assertions.assertEquals(calculadorDePuntosObtenidos.calcularPuntos(persona), 4356.0);
    }
}
