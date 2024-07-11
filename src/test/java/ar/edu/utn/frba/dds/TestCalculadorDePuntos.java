package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.puntosDeColaboracion.*;
import ar.edu.utn.frba.dds.models.colaboracion.*;
import ar.edu.utn.frba.dds.models.data.Comida;
import ar.edu.utn.frba.dds.models.heladera.*;
import ar.edu.utn.frba.dds.models.tarjeta.*;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import ar.edu.utn.frba.dds.utils.GeneradorDeCodigoTarjeta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCalculadorDePuntos {
  Colaborador persona = Colaborador.persona();
  Vianda vianda1 = new Vianda(new Comida("arroz", 400), null, 23);
  Vianda vianda2 = new Vianda(new Comida("pollo", 520), null, 24);
  Vianda vianda3 = new Vianda(new Comida("carne", 600), null, 27);
  Vianda vianda4 = new Vianda(new Comida("sopa", 250), null, 20);
  Vianda vianda5 = new Vianda(new Comida("asado", 500), null, 26);

  List<Vianda> listaViandas1 = new ArrayList<>() {{
    add(vianda1);
    add(vianda2);
  }};
  List<Vianda> listaViandas2 = new ArrayList<>() {{
    add(vianda3);
  }};
  List<Vianda> listaViandas3 = new ArrayList<>() {{
    add(vianda4);
    add(vianda5);
    add(vianda3);
  }};
  Heladera heladera1 = Heladera.with(9);
  Heladera heladera2 = Heladera.with(5);
  Heladera heladera3 = Heladera.with(2);
  PuntosTarjetasDonadas calculadorTarjetasDonadas = new PuntosTarjetasDonadas();
  PuntosDonacionDinero calculadorDonacionDinero = new PuntosDonacionDinero();
  PuntosDonacionViandas calculadorDonacionViandas = new PuntosDonacionViandas();
  PuntosHeladerasActivas calculadorHeladerasActivas = new PuntosHeladerasActivas();
  PuntosDistribucionViandas calculadorDistribucionViandas = new PuntosDistribucionViandas();

  @Test
  @DisplayName("Los puntos por repartir tarjetas es la cantidad de tarjetas repartidas multiplicado por 2")
  public void puntosTarjetas() {
    calculadorTarjetasDonadas.setListaTarjetasRepartidas(new ArrayList<TarjetaPersonaVulnerable>() {{
      add(TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar()));
      add(TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar()));
      add(TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar()));
    }});
    Assertions.assertEquals(calculadorTarjetasDonadas.calcularPuntos(persona), 6.0);
  }

  @Test
  @DisplayName("Los puntos por donar dinero es la cantidad de dinero donado multiplicado por 0.5")
  public void puntosDonacionDinero() {
    calculadorDonacionDinero.setListaDonacionesDinero(new ArrayList<>() {{
      add(new DonacionDinero(persona, 500));
      add(new DonacionDinero(persona, 200));
      add(new DonacionDinero(persona, 10000));
    }});
    Assertions.assertEquals(calculadorDonacionDinero.calcularPuntos(persona), 5350.0);
  }

  @Test
  @DisplayName("Los puntos por distribuir viandas es la cantidad de viandas distribuidas multiplicado por 1")
  public void puntosDistribucionViandas() {
    calculadorDistribucionViandas.setListaViandasDistribuidas(new ArrayList<>() {{
      add(new DistribucionViandas(null, null, listaViandas1, persona));
      add(new DistribucionViandas(null, null, listaViandas2, persona));
      add(new DistribucionViandas(null, null, listaViandas3, persona));
    }});
    Assertions.assertEquals(calculadorDistribucionViandas.calcularPuntos(persona), 3.0);
  }

  @Test
  @DisplayName("Los puntos por hacerse cargo de heladeras activas es la cantidad de heladeras activas multiplicado por la sumatoria de meses activa y por 5")
  public void puntosHeladerasActivas() {
    heladera1.setFechaInicioFuncionamiento(LocalDate.of(2024, 3, 2));
    heladera3.setFechaInicioFuncionamiento(LocalDate.of(2024, 1, 1));
    heladera2.setEstado(EstadoHeladera.INACTIVA);

    calculadorHeladerasActivas.setListaHeladerasACargo(new ArrayList<>() {{
      add(new HacerseCargoHeladera(persona, heladera1));
      add(new HacerseCargoHeladera(persona, heladera2));
      add(new HacerseCargoHeladera(persona, heladera3));
    }});

    Assertions.assertEquals(calculadorHeladerasActivas.calcularPuntos(persona), 80.0);
  }

  @Test
  @DisplayName("Los puntos por donar viandas es la cantidad de viandas donadas multiplicado por 1.5")
  public void puntosDonacionViandas() {
    calculadorDonacionViandas.setListaViandasDonadas(new ArrayList<>() {{
      add(new DonacionVianda(persona, vianda1, heladera1));
      add(new DonacionVianda(persona, vianda2, heladera2));
    }});
    Assertions.assertEquals(calculadorDonacionViandas.calcularPuntos(persona), 3.0);
  }


  @Test
  @DisplayName("Los puntos obtenidos de una persona es la sumatoria de los puntos por cada forma colaborada restado a sus puntos canjeados")
  public void puntosObtenidos() {
    calculadorDonacionDinero.setListaDonacionesDinero(new ArrayList<>() {{
      add(new DonacionDinero(persona, 500));
      add(new DonacionDinero(persona, 200));
      add(new DonacionDinero(persona, 10000));
    }});
    calculadorTarjetasDonadas.setListaTarjetasRepartidas(new ArrayList<>() {{
      add(TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar()));
      add(TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar()));
      add(TarjetaPersonaVulnerable.with(GeneradorDeCodigoTarjeta.generar()));
    }});
    PuntosPorColaboracion calculadorDePuntosObtenidos = new PuntosPorColaboracion(new ArrayList<>() {{
      add(calculadorDonacionDinero);
      add(calculadorTarjetasDonadas);
    }});

    persona.setPuntosCanjeados(1000.0);
    Assertions.assertEquals(calculadorDePuntosObtenidos.calcularPuntos(persona), 4356.0);
  }
}
