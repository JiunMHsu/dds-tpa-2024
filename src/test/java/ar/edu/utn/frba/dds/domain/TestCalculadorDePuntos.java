package ar.edu.utn.frba.dds.domain;

public class TestCalculadorDePuntos {

//  private Colaborador persona;
//  private Colaborador otraPersona;
//
//  private CanjeDePuntosRepository canjeDePuntosRepository;
//
//  @BeforeEach
//  public void setup() {
//    persona = Colaborador.colaborador(Usuario.empty());
//    otraPersona = Colaborador.colaborador(Usuario.empty());
//    canjeDePuntosRepository = new CanjeDePuntosRepository();
//
//    // Donacion Dinero. Los puntos por donar dinero es la cantidad de dinero donado multiplicado por 0.5
//    // Puntos: (500 + 200 + 10000) * 0.5 = 5350
//    DonacionDinero donacionDinero1 = DonacionDinero.por(persona, LocalDate.of(2024, 2, 1).atStartOfDay(), 500);
//    DonacionDinero donacionDinero2 = DonacionDinero.por(persona, LocalDate.of(2024, 2, 1).atStartOfDay(), 200);
//    DonacionDinero donacionDinero3 = DonacionDinero.por(persona, LocalDate.of(2024, 2, 1).atStartOfDay(), 10000);
//
//    DonacionDineroRepository.guardar(donacionDinero1);
//    DonacionDineroRepository.guardar(donacionDinero2);
//    DonacionDineroRepository.guardar(donacionDinero3);
//
//    // Distribucion Viandas. Los puntos por distribuir viandas es la cantidad de viandas distribuidas multiplicada por 1
//    // Puntos: (4 + 10 + 6) * 1 = 20
//    DistribucionViandas distribucion1 = DistribucionViandas.por(persona, LocalDate.of(2024, 3, 2).atStartOfDay(), 4);
//    DistribucionViandas distribucion2 = DistribucionViandas.por(persona, LocalDate.of(2024, 3, 2).atStartOfDay(), 10);
//    DistribucionViandas distribucion3 = DistribucionViandas.por(persona, LocalDate.of(2024, 3, 2).atStartOfDay(), 6);
//
//    DistribucionViandasRepository.guardar(distribucion1);
//    DistribucionViandasRepository.guardar(distribucion2);
//    DistribucionViandasRepository.guardar(distribucion3);
//
//    // Donacion Viandas. Los puntos por donar viandas es la cantidad de viandas donadas multiplicada por 1.5
//    // Puntos: (1 + 1 + 1) * 1.5 = 4.5
//    DonacionVianda donacionVianda1 = DonacionVianda.por(persona, LocalDate.of(2024, 1, 6).atStartOfDay());
//    DonacionVianda donacionVianda2 = DonacionVianda.por(persona, LocalDate.of(2024, 2, 12).atStartOfDay());
//    DonacionVianda donacionVianda3 = DonacionVianda.por(persona, LocalDate.of(2024, 1, 8).atStartOfDay());
//
//    DonacionViandaRepository.guardar(donacionVianda1);
//    DonacionViandaRepository.guardar(donacionVianda2);
//    DonacionViandaRepository.guardar(donacionVianda3);
//
//    // Reparto Tarjetas. Los puntos por repartir tarjetas es la cantidad de tarjetas repartidas multiplicada por 2
//    // Puntos: (1 + 1 + 1) * 2 = 6
//    RepartoDeTarjetas repartoDeTarjetas1 = RepartoDeTarjetas.por(persona, LocalDate.of(2024, 1, 5).atStartOfDay());
//    RepartoDeTarjetas repartoDeTarjetas2 = RepartoDeTarjetas.por(persona, LocalDate.of(2024, 1, 5).atStartOfDay());
//    RepartoDeTarjetas repartoDeTarjetas3 = RepartoDeTarjetas.por(persona, LocalDate.of(2024, 1, 5).atStartOfDay());
//
//    RepartoDeTarjetasRepository.guardar(repartoDeTarjetas1);
//    RepartoDeTarjetasRepository.guardar(repartoDeTarjetas2);
//    RepartoDeTarjetasRepository.guardar(repartoDeTarjetas3);
//
//    // Hacerse Cargo Heladera. Los puntos por hacerse cargo de heladeras activas es
//    // la cantidad de heladeras activas multiplicada por la sumatoria de meses activa y por 5
//    // Puntos: (1 + 1) * (4 + 6) * 5 = 100 POR MES DE JULIO
//    // Revisar cuentas según mes
//    Heladera heladera1 = Heladera.con(10);
//    Heladera heladera2 = Heladera.con(10);
//    Heladera heladera3 = Heladera.con(10);
//
//    heladera1.setEstado(EstadoHeladera.ACTIVA);
//    heladera2.setEstado(EstadoHeladera.INACTIVA);
//    heladera3.setEstado(EstadoHeladera.ACTIVA);
//
//    heladera1.setInicioFuncionamiento(LocalDate.of(2024, 3, 2));
//    heladera3.setInicioFuncionamiento(LocalDate.of(2024, 1, 1));
//
//    HacerseCargoHeladera hacerseCargoHeladera1 = HacerseCargoHeladera.por(persona, heladera1);
//    HacerseCargoHeladera hacerseCargoHeladera2 = HacerseCargoHeladera.por(persona, heladera2);
//    HacerseCargoHeladera hacerseCargoHeladera3 = HacerseCargoHeladera.por(persona, heladera3);
//
//    HacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera1);
//    HacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera2);
//    HacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera3);
//
//  }
//
//  @Test
//  @DisplayName("Los puntos obtenidos de una persona, si no hubo un canjeo anterior, es la sumatoria de los puntos por cada forma colaborada")
//  public void puntosObtenidosPrimerCanjeo() {
//    PuntosPorColaboracion calculadorDePuntos = PuntosPorColaboracion.of(persona);
//    Assertions.assertEquals(5480.5, calculadorDePuntos.calcularPuntos());
//  }
//
//  @Test
//  @DisplayName("Los puntos obtenidos de una persona, si hubo canjeos anteriores, es los nuevos puntos por heladeras activas mas los puntos sobrantes")
//  public void puntosObtenidosConCanjeoAnterior() {
//
//    // puntos totales mes abril: 5420.5
//    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.por(otraPersona, "lapiz", 420.5); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos primerCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 4, 12).atStartOfDay(), 420.5, 5000.0, ofertaDeProductos);
//    canjeDePuntosRepository.guardar(primerCanjeo);
//
//    // puntos totales mayo luego de primer canjeo: 5020.0
//    OfertaDeProductos ofertaDeProductos2 = OfertaDeProductos.por(otraPersona, "birome", 120.0); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos segundoCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 5, 20).atStartOfDay(), 120.0, 4900.0, ofertaDeProductos2);
//    canjeDePuntosRepository.guardar(segundoCanjeo);
//
//    PuntosPorColaboracion calculadorDePuntos2 = PuntosPorColaboracion.of(persona);
//    Assertions.assertEquals(4940.0, calculadorDePuntos2.calcularPuntos());
//
//  }
//
//  @Test
//  @DisplayName("Los puntos obtenidos de una persona, si hubo canjeos anteriores y nuevas colaboraciones, es la sumatoria de los puntos por cada forma colaborada realizada luego del ultimo canjeo, sumado a los puntos sobrantes")
//  public void puntosObtenidosConCanjeoAnteriorMasNuevasColab() {
//
//    // puntos totales abril: 5420.5
//    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.por(otraPersona, "lapiz", 420.5); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos primerCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 4, 12), 420.5, 5000.0, ofertaDeProductos);
//    CanjeDePuntosRepository.guardar(primerCanjeo);
//
//    // puntos totales mayo luego de primer canjeo: 5020.0
//    OfertaDeProductos ofertaDeProductos2 = OfertaDeProductos.por(otraPersona, "birome", 120.0); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos segundoCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 5, 20), 120.0, 4900.0, ofertaDeProductos2);
//    CanjeDePuntosRepository.guardar(segundoCanjeo);
//
//    // nuevas colaboraciones
//    // puntaje donacion dinero: 1000 * 0.5 = 500
//    DonacionDinero donacionDinero4 = DonacionDinero.por(persona, LocalDate.of(2024, 6, 1).atStartOfDay(), 1000);
//    DonacionDineroRepository.guardar(donacionDinero4);
//
//    // puntaje distribucion viandas : 20
//    DistribucionViandas distribucion4 = DistribucionViandas.por(persona, LocalDate.of(2024, 5, 2).atStartOfDay(), 20);
//    DistribucionViandasRepository.guardar(distribucion4);
//
//    // puntaje heladeras : 3((6-3)+(6-1)+(6-5))5 - 2((5-3)+(5-1))5 = 135 - 60 = 75
//    Heladera heladera4 = Heladera.con(10);
//    heladera4.setEstado(EstadoHeladera.ACTIVA);
//    heladera4.setInicioFuncionamiento(LocalDate.of(2024, 5, 8));
//    HacerseCargoHeladera hacerseCargoHeladera4 = HacerseCargoHeladera.por(persona, heladera4);
//    HacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera4);
//    // puntaje total julio antes tercer canjeo: 4940.0 + 595.0 = 5535.0
//
//    OfertaDeProductos ofertaDeProductos3 = OfertaDeProductos.por(otraPersona, "pluma", 535.0); //en vez de calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos tercerCanjeo = CanjeDePuntos.por(persona, LocalDate.of(2024, 6, 20).atStartOfDay(), 535.0, 5000.0, ofertaDeProductos3);
//    canjeDePuntosRepository.guardar(tercerCanjeo);
//
//    // heladeras : 3((7-3)+(7-1)+(7-5))5 - 3((6-3)+(6-1)+(6-5))5 = 180 - 135 = 45
//    PuntosPorColaboracion calculadorDePuntos3 = PuntosPorColaboracion.of(persona);
//    Assertions.assertEquals(5045.0, calculadorDePuntos3.calcularPuntos());
//  }

}



