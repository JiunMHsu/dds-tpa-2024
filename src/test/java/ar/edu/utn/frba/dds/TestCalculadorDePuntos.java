package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.colaboracion.*;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.colaborador.Usuario;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.PuntosPorColaboracion;
import ar.edu.utn.frba.dds.repository.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.repository.colaboracion.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestCalculadorDePuntos {

  Colaborador persona = Colaborador.colaborador(new Usuario("", "", ""));
  Colaborador otraPersona = Colaborador.colaborador(new Usuario("", "", ""));
  PuntosPorColaboracion calculadorDePuntos = PuntosPorColaboracion.of(persona);

  @BeforeEach
  public void setup() {
    // Donacion Dinero. Los puntos por donar dinero es la cantidad de dinero donado multiplicado por 0.5
    // Puntos : (500+200+10000)*0.5 = 5350
    DonacionDinero donacionDinero1 = DonacionDinero.by(persona, LocalDate.of(2024, 2, 1) , 500);
    DonacionDinero donacionDinero2 = DonacionDinero.by(persona,LocalDate.of(2024, 2, 1) , 200);
    DonacionDinero donacionDinero3 = DonacionDinero.by(persona,LocalDate.of(2024, 2, 1) , 10000);

    DonacionDineroRepository.agregar(donacionDinero1);
    DonacionDineroRepository.agregar(donacionDinero2);
    DonacionDineroRepository.agregar(donacionDinero3);

    // Distribucion Viandas. Los puntos por distribuir viandas es la cantidad de viandas distribuidas multiplicado por 1
    // Puntos : (4+10+6)*1 = 20
    DistribucionViandas distribucion1 = DistribucionViandas.by(persona, LocalDate.of(2024, 3, 2), 4);
    DistribucionViandas distribucion2 = DistribucionViandas.by(persona, LocalDate.of(2024, 4, 2), 10);
    DistribucionViandas distribucion3 = DistribucionViandas.by(persona, LocalDate.of(2024, 5, 2), 6);

    DistribucionViandasRepository.agregar(distribucion1);
    DistribucionViandasRepository.agregar(distribucion2);
    DistribucionViandasRepository.agregar(distribucion3);

    // Donacion Viandas. Los puntos por donar viandas es la cantidad de viandas donadas multiplicado por 1.5
    // Puntos : (1+1+1)*1.5 = 4.5
    DonacionVianda donacionVianda1 = DonacionVianda.by(persona, LocalDate.of(2024, 5, 6));
    DonacionVianda donacionVianda2 = DonacionVianda.by(persona, LocalDate.of(2024, 2, 12));
    DonacionVianda donacionVianda3 = DonacionVianda.by(persona, LocalDate.of(2024, 1, 8));

    DonacionViandaRepository.agregar(donacionVianda1);
    DonacionViandaRepository.agregar(donacionVianda2);
    DonacionViandaRepository.agregar(donacionVianda3);

    // Reparto Tarjetas. Los puntos por repartir tarjetas es la cantidad de tarjetas repartidas multiplicado por 2
    // Puntos : (1+1+1)*2 = 6
    RepartoDeTarjetas repartoDeTarjetas1 = RepartoDeTarjetas.by(persona, LocalDate.of(2024, 5, 5));
    RepartoDeTarjetas repartoDeTarjetas2 = RepartoDeTarjetas.by(persona, LocalDate.of(2024, 5, 5));
    RepartoDeTarjetas repartoDeTarjetas3 = RepartoDeTarjetas.by(persona, LocalDate.of(2024, 5, 5));

    RepartoDeTarjetasRepository.agregar(repartoDeTarjetas1);
    RepartoDeTarjetasRepository.agregar(repartoDeTarjetas2);
    RepartoDeTarjetasRepository.agregar(repartoDeTarjetas3);

    // Hacerse Cargo Heladera. Los puntos por hacerse cargo de heladeras activas es la cantidad de heladeras activas multiplicado por la sumatoria de meses activa y por 5
    // Puntos : (1+1) * (4+6) * 5 = 100 POR MES DE JULIO
    // Revisar cuentas segun mes
    Heladera heladera1 = Heladera.with(10);
    Heladera heladera2 = Heladera.with(10);
    Heladera heladera3 = Heladera.with(10);

    heladera1.setEstado(EstadoHeladera.ACTIVA);
    heladera2.setEstado(EstadoHeladera.INACTIVA);
    heladera3.setEstado(EstadoHeladera.ACTIVA);

    heladera1.setInicioFuncionamiento(LocalDate.of(2024, 3, 2));
    heladera3.setInicioFuncionamiento(LocalDate.of(2024, 1, 1));

    HacerseCargoHeladera hacerseCargoHeladera1 = HacerseCargoHeladera.with(persona, heladera1);
    HacerseCargoHeladera hacerseCargoHeladera2 = HacerseCargoHeladera.with(persona, heladera2);
    HacerseCargoHeladera hacerseCargoHeladera3 = HacerseCargoHeladera.with(persona, heladera3);

    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera1);
    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera2);
    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera3);

  }

  @Test
  @DisplayName("Los puntos obtenidos de una persona, si no hubo un canjeo anterior, es la sumatoria de los puntos por cada forma colaborada")
  public void puntosObtenidosPrimerCanjeo() {
    Assertions.assertEquals(calculadorDePuntos.calcularPuntos(), 5480.5);
  }

  @Test
  @DisplayName("Los puntos obtenidos de una persona, si hubo canjeos anteriores, es los nuevos puntos por heladeras activas mas los puntos sobrantes")
  public void puntosObtenidosConCanjeoAnterior() {

    // puntos totales del canjeo anterior mayo: 5440.5
    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.with(otraPersona,"lapiz" , 440.5); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
    CanjeDePuntos primerCanjeo = CanjeDePuntos.with(persona, LocalDate.of(2024,5,12), 440.5, 5000.0, ofertaDeProductos);
    CanjeDePuntosRepository.agregar(primerCanjeo);
    PuntosPorColaboracion calculadorDePuntos2 = PuntosPorColaboracion.of(persona);
    Assertions.assertEquals(calculadorDePuntos2.calcularPuntos(), 5040.0);

  }
  @Test
  @DisplayName("Los puntos obtenidos de una persona, si hubo canjeos anteriores y nuevas colaboraciones, es la sumatoria de los puntos por cada forma colaborada realizada luego del ultimo canjeo, sumado a los puntos sobrantes")
  public void puntosObtenidosConCanjeoAnteriorMasNuevasColab() {


  }

}



