package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.colaboracion.*;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.usuario.Usuario;
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

  private Colaborador persona;
  private Colaborador otraPersona;

  @BeforeEach
  public void setup() {
    persona = Colaborador.colaborador(Usuario.empty());
    otraPersona = Colaborador.colaborador(Usuario.empty());

    // Donacion Dinero. Los puntos por donar dinero es la cantidad de dinero donado multiplicado por 0.5
    // Puntos: (500 + 200 + 10000) * 0.5 = 5350
    DonacionDinero donacionDinero1 = DonacionDinero.por(persona, LocalDate.of(2024, 2, 1), 500);
    DonacionDinero donacionDinero2 = DonacionDinero.por(persona, LocalDate.of(2024, 2, 1), 200);
    DonacionDinero donacionDinero3 = DonacionDinero.por(persona, LocalDate.of(2024, 2, 1), 10000);

    DonacionDineroRepository.agregar(donacionDinero1);
    DonacionDineroRepository.agregar(donacionDinero2);
    DonacionDineroRepository.agregar(donacionDinero3);

    // Distribucion Viandas. Los puntos por distribuir viandas es la cantidad de viandas distribuidas multiplicada por 1
    // Puntos: (4 + 10 + 6) * 1 = 20
    DistribucionViandas distribucion1 = DistribucionViandas.por(persona, LocalDate.of(2024, 3, 2), 4);
    DistribucionViandas distribucion2 = DistribucionViandas.por(persona, LocalDate.of(2024, 3, 2), 10);
    DistribucionViandas distribucion3 = DistribucionViandas.por(persona, LocalDate.of(2024, 3, 2), 6);

    DistribucionViandasRepository.agregar(distribucion1);
    DistribucionViandasRepository.agregar(distribucion2);
    DistribucionViandasRepository.agregar(distribucion3);

    // Donacion Viandas. Los puntos por donar viandas es la cantidad de viandas donadas multiplicada por 1.5
    // Puntos: (1 + 1 + 1) * 1.5 = 4.5
    DonacionVianda donacionVianda1 = DonacionVianda.por(persona, LocalDate.of(2024, 1, 6));
    DonacionVianda donacionVianda2 = DonacionVianda.por(persona, LocalDate.of(2024, 2, 12));
    DonacionVianda donacionVianda3 = DonacionVianda.por(persona, LocalDate.of(2024, 1, 8));

    DonacionViandaRepository.agregar(donacionVianda1);
    DonacionViandaRepository.agregar(donacionVianda2);
    DonacionViandaRepository.agregar(donacionVianda3);

    // Reparto Tarjetas. Los puntos por repartir tarjetas es la cantidad de tarjetas repartidas multiplicada por 2
    // Puntos: (1 + 1 + 1) * 2 = 6
    RepartoDeTarjetas repartoDeTarjetas1 = RepartoDeTarjetas.por(persona, LocalDate.of(2024, 1, 5));
    RepartoDeTarjetas repartoDeTarjetas2 = RepartoDeTarjetas.por(persona, LocalDate.of(2024, 1, 5));
    RepartoDeTarjetas repartoDeTarjetas3 = RepartoDeTarjetas.por(persona, LocalDate.of(2024, 1, 5));

    RepartoDeTarjetasRepository.agregar(repartoDeTarjetas1);
    RepartoDeTarjetasRepository.agregar(repartoDeTarjetas2);
    RepartoDeTarjetasRepository.agregar(repartoDeTarjetas3);

    // Hacerse Cargo Heladera. Los puntos por hacerse cargo de heladeras activas es
    // la cantidad de heladeras activas multiplicada por la sumatoria de meses activa y por 5
    // Puntos: (1 + 1) * (4 + 6) * 5 = 100 POR MES DE JULIO
    // Revisar cuentas según mes
    Heladera heladera1 = Heladera.con(10);
    Heladera heladera2 = Heladera.con(10);
    Heladera heladera3 = Heladera.con(10);

    heladera1.setEstado(EstadoHeladera.ACTIVA);
    heladera2.setEstado(EstadoHeladera.INACTIVA);
    heladera3.setEstado(EstadoHeladera.ACTIVA);

    heladera1.setInicioFuncionamiento(LocalDate.of(2024, 3, 2));
    heladera3.setInicioFuncionamiento(LocalDate.of(2024, 1, 1));

    HacerseCargoHeladera hacerseCargoHeladera1 = HacerseCargoHeladera.por(persona, heladera1);
    HacerseCargoHeladera hacerseCargoHeladera2 = HacerseCargoHeladera.por(persona, heladera2);
    HacerseCargoHeladera hacerseCargoHeladera3 = HacerseCargoHeladera.por(persona, heladera3);

    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera1);
    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera2);
    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera3);

  }

  @Test
  @DisplayName("Los puntos obtenidos de una persona, si no hubo un canjeo anterior, es la sumatoria de los puntos por cada forma colaborada")
  public void puntosObtenidosPrimerCanjeo() {
    PuntosPorColaboracion calculadorDePuntos = PuntosPorColaboracion.of(persona);
    Assertions.assertEquals(5480.5, calculadorDePuntos.calcularPuntos());
  }

  @Test
  @DisplayName("Los puntos obtenidos de una persona, si hubo canjeos anteriores, es los nuevos puntos por heladeras activas mas los puntos sobrantes")
  public void puntosObtenidosConCanjeoAnterior() {

    // puntos totales mes abril: 5420.5
    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.por(otraPersona, "lapiz", 420.5); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
    CanjeDePuntos primerCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 4, 12), 420.5, 5000.0, ofertaDeProductos);
    CanjeDePuntosRepository.agregar(primerCanjeo);

    // puntos totales mayo luego de primer canjeo: 5020.0
    OfertaDeProductos ofertaDeProductos2 = OfertaDeProductos.por(otraPersona, "birome", 120.0); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
    CanjeDePuntos segundoCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 5, 20), 120.0, 4900.0, ofertaDeProductos2);
    CanjeDePuntosRepository.agregar(segundoCanjeo);

    PuntosPorColaboracion calculadorDePuntos2 = PuntosPorColaboracion.of(persona);
    Assertions.assertEquals(4940.0, calculadorDePuntos2.calcularPuntos());

  }

  @Test
  @DisplayName("Los puntos obtenidos de una persona, si hubo canjeos anteriores y nuevas colaboraciones, es la sumatoria de los puntos por cada forma colaborada realizada luego del ultimo canjeo, sumado a los puntos sobrantes")
  public void puntosObtenidosConCanjeoAnteriorMasNuevasColab() {

    // puntos totales abril: 5420.5
    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.por(otraPersona, "lapiz", 420.5); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
    CanjeDePuntos primerCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 4, 12), 420.5, 5000.0, ofertaDeProductos);
    CanjeDePuntosRepository.agregar(primerCanjeo);

    // puntos totales mayo luego de primer canjeo: 5020.0
    OfertaDeProductos ofertaDeProductos2 = OfertaDeProductos.por(otraPersona, "birome", 120.0); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
    CanjeDePuntos segundoCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 5, 20), 120.0, 4900.0, ofertaDeProductos2);
    CanjeDePuntosRepository.agregar(segundoCanjeo);

    // nuevas colaboraciones
    // puntaje donacion dinero: 1000 * 0.5 = 500
    DonacionDinero donacionDinero4 = DonacionDinero.por(persona, LocalDate.of(2024, 6, 1), 1000);
    DonacionDineroRepository.agregar(donacionDinero4);

    // puntaje distribucion viandas : 20
    DistribucionViandas distribucion4 = DistribucionViandas.por(persona, LocalDate.of(2024, 5, 2), 20);
    DistribucionViandasRepository.agregar(distribucion4);

    // puntaje heladeras : 3((6-3)+(6-1)+(6-5))5 - 2((5-3)+(5-1))5 = 135 - 60 = 75
    Heladera heladera4 = Heladera.con(10);
    heladera4.setEstado(EstadoHeladera.ACTIVA);
    heladera4.setInicioFuncionamiento(LocalDate.of(2024, 5, 8));
    HacerseCargoHeladera hacerseCargoHeladera4 = HacerseCargoHeladera.por(persona, heladera4);
    HacerseCargoHeladeraRepository.agregar(hacerseCargoHeladera4);
    // puntaje total julio antes tercer canjeo: 4940.0 + 595.0 = 5535.0

    OfertaDeProductos ofertaDeProductos3 = OfertaDeProductos.por(otraPersona, "pluma", 535.0); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
    CanjeDePuntos tercerCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 6, 20), 535.0, 5000.0, ofertaDeProductos3);
    CanjeDePuntosRepository.agregar(tercerCanjeo);

    // heladeras : 3((7-3)+(7-1)+(7-5))5 - 3((6-3)+(6-1)+(6-5))5 = 180 - 135 = 45
    PuntosPorColaboracion calculadorDePuntos3 = PuntosPorColaboracion.of(persona);
    Assertions.assertEquals(5045.0, calculadorDePuntos3.calcularPuntos());
  }

}



