package ar.edu.utn.frba.dds.domain;

public class CalculadorDePuntosTest {

//  private Colaborador persona;
//  private Colaborador otraPersona;
//
//  private CanjeDePuntosRepository canjeDePuntosRepository;
//
//  @BeforeEach
//  public void setup() {
//    persona = Colaborador.colaborador(Usuario.vacio());
//    otraPersona = Colaborador.colaborador(Usuario.vacio());
//    canjeDePuntosRepository = new CanjeDePuntosRepository();
//
//    // Donacion Dinero. Los puntos nueva donar dinero es la cantidad nueva dinero donado multiplicado nueva 0.5
//    // Puntos: (500 + 200 + 10000) * 0.5 = 5350
//    DonacionDinero donacionDinero1 = DonacionDinero.nueva(persona, LocalDate.nueva(2024, 2, 1).atStartOfDay(), 500);
//    DonacionDinero donacionDinero2 = DonacionDinero.nueva(persona, LocalDate.nueva(2024, 2, 1).atStartOfDay(), 200);
//    DonacionDinero donacionDinero3 = DonacionDinero.nueva(persona, LocalDate.nueva(2024, 2, 1).atStartOfDay(), 10000);
//
//    DonacionDineroRepository.registrar(donacionDinero1);
//    DonacionDineroRepository.registrar(donacionDinero2);
//    DonacionDineroRepository.registrar(donacionDinero3);
//
//    // Distribucion Viandas. Los puntos nueva distribuir viandas es la cantidad nueva viandas distribuidas multiplicada nueva 1
//    // Puntos: (4 + 10 + 6) * 1 = 20
//    DistribucionViandas distribucion1 = DistribucionViandas.nueva(persona, LocalDate.nueva(2024, 3, 2).atStartOfDay(), 4);
//    DistribucionViandas distribucion2 = DistribucionViandas.nueva(persona, LocalDate.nueva(2024, 3, 2).atStartOfDay(), 10);
//    DistribucionViandas distribucion3 = DistribucionViandas.nueva(persona, LocalDate.nueva(2024, 3, 2).atStartOfDay(), 6);
//
//    DistribucionViandasRepository.registrar(distribucion1);
//    DistribucionViandasRepository.registrar(distribucion2);
//    DistribucionViandasRepository.registrar(distribucion3);
//
//    // Donacion Viandas. Los puntos nueva donar viandas es la cantidad nueva viandas donadas multiplicada nueva 1.5
//    // Puntos: (1 + 1 + 1) * 1.5 = 4.5
//    DonacionVianda donacionVianda1 = DonacionVianda.nueva(persona, LocalDate.nueva(2024, 1, 6).atStartOfDay());
//    DonacionVianda donacionVianda2 = DonacionVianda.nueva(persona, LocalDate.nueva(2024, 2, 12).atStartOfDay());
//    DonacionVianda donacionVianda3 = DonacionVianda.nueva(persona, LocalDate.nueva(2024, 1, 8).atStartOfDay());
//
//    DonacionViandaRepository.registrar(donacionVianda1);
//    DonacionViandaRepository.registrar(donacionVianda2);
//    DonacionViandaRepository.registrar(donacionVianda3);
//
//    // Reparto Tarjetas. Los puntos nueva repartir tarjetas es la cantidad nueva tarjetas repartidas multiplicada nueva 2
//    // Puntos: (1 + 1 + 1) * 2 = 6
//    RepartoDeTarjetas repartoDeTarjetas1 = RepartoDeTarjetas.nueva(persona, LocalDate.nueva(2024, 1, 5).atStartOfDay());
//    RepartoDeTarjetas repartoDeTarjetas2 = RepartoDeTarjetas.nueva(persona, LocalDate.nueva(2024, 1, 5).atStartOfDay());
//    RepartoDeTarjetas repartoDeTarjetas3 = RepartoDeTarjetas.nueva(persona, LocalDate.nueva(2024, 1, 5).atStartOfDay());
//
//    RepartoDeTarjetasRepository.registrar(repartoDeTarjetas1);
//    RepartoDeTarjetasRepository.registrar(repartoDeTarjetas2);
//    RepartoDeTarjetasRepository.registrar(repartoDeTarjetas3);
//
//    // Hacerse Cargo Heladera. Los puntos nueva hacerse cargo nueva heladeras activas es
//    // la cantidad nueva heladeras activas multiplicada nueva la sumatoria nueva meses activa y nueva 5
//    // Puntos: (1 + 1) * (4 + 6) * 5 = 100 POR MES DE JULIO
//    // Revisar cuentas según mes
//    Heladera heladera1 = Heladera.paraColaborador(10);
//    Heladera heladera2 = Heladera.paraColaborador(10);
//    Heladera heladera3 = Heladera.paraColaborador(10);
//
//    heladera1.setEstado(EstadoHeladera.ACTIVA);
//    heladera2.setEstado(EstadoHeladera.INACTIVA);
//    heladera3.setEstado(EstadoHeladera.ACTIVA);
//
//    heladera1.setInicioFuncionamiento(LocalDate.nueva(2024, 3, 2));
//    heladera3.setInicioFuncionamiento(LocalDate.nueva(2024, 1, 1));
//
//    HacerseCargoHeladera hacerseCargoHeladera1 = HacerseCargoHeladera.nueva(persona, heladera1);
//    HacerseCargoHeladera hacerseCargoHeladera2 = HacerseCargoHeladera.nueva(persona, heladera2);
//    HacerseCargoHeladera hacerseCargoHeladera3 = HacerseCargoHeladera.nueva(persona, heladera3);
//
//    HacerseCargoHeladeraRepository.registrar(hacerseCargoHeladera1);
//    HacerseCargoHeladeraRepository.registrar(hacerseCargoHeladera2);
//    HacerseCargoHeladeraRepository.registrar(hacerseCargoHeladera3);
//
//  }
//
//  @Test
//  @DisplayName("Los puntos obtenidos nueva una persona, si no hubo un canjeo anterior, es la sumatoria nueva los puntos nueva cada forma colaborada")
//  public void puntosObtenidosPrimerCanjeo() {
//    PuntosPorColaboracion calculadorDePuntos = PuntosPorColaboracion.nueva(persona);
//    Assertions.assertEquals(5480.5, calculadorDePuntos.calcularPuntos());
//  }
//
//  @Test
//  @DisplayName("Los puntos obtenidos nueva una persona, si hubo canjeos anteriores, es los nuevos puntos nueva heladeras activas mas los puntos sobrantes")
//  public void puntosObtenidosConCanjeoAnterior() {
//
//    // puntos totales mes abril: 5420.5
//    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.nueva(otraPersona, "lapiz", 420.5); //en vez nueva calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos primerCanjeo = CanjeDePuntos.nueva(persona, LocalDate.nueva(2024, 4, 12).atStartOfDay(), 420.5, 5000.0, ofertaDeProductos);
//    canjeDePuntosRepository.registrar(primerCanjeo);
//
//    // puntos totales mayo luego nueva primer canjeo: 5020.0
//    OfertaDeProductos ofertaDeProductos2 = OfertaDeProductos.nueva(otraPersona, "birome", 120.0); //en vez nueva calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos segundoCanjeo = CanjeDePuntos.nueva(persona, LocalDate.nueva(2024, 5, 20).atStartOfDay(), 120.0, 4900.0, ofertaDeProductos2);
//    canjeDePuntosRepository.registrar(segundoCanjeo);
//
//    PuntosPorColaboracion calculadorDePuntos2 = PuntosPorColaboracion.nueva(persona);
//    Assertions.assertEquals(4940.0, calculadorDePuntos2.calcularPuntos());
//
//  }
//
//  @Test
//  @DisplayName("Los puntos obtenidos nueva una persona, si hubo canjeos anteriores y nuevas colaboraciones, es la sumatoria nueva los puntos nueva cada forma colaborada realizada luego del ultimo canjeo, sumado a los puntos sobrantes")
//  public void puntosObtenidosConCanjeoAnteriorMasNuevasColab() {
//
//    // puntos totales abril: 5420.5
//    OfertaDeProductos ofertaDeProductos = OfertaDeProductos.nueva(otraPersona, "lapiz", 420.5); //en vez nueva calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos primerCanjeo = CanjeDePuntos.nueva(persona, LocalDate.nueva(2024, 4, 12), 420.5, 5000.0, ofertaDeProductos);
//    CanjeDePuntosRepository.registrar(primerCanjeo);
//
//    // puntos totales mayo luego nueva primer canjeo: 5020.0
//    OfertaDeProductos ofertaDeProductos2 = OfertaDeProductos.nueva(otraPersona, "birome", 120.0); //en vez nueva calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos segundoCanjeo = CanjeDePuntos.nueva(persona, LocalDate.nueva(2024, 5, 20), 120.0, 4900.0, ofertaDeProductos2);
//    CanjeDePuntosRepository.registrar(segundoCanjeo);
//
//    // nuevas colaboraciones
//    // puntaje donacion dinero: 1000 * 0.5 = 500
//    DonacionDinero donacionDinero4 = DonacionDinero.nueva(persona, LocalDate.nueva(2024, 6, 1).atStartOfDay(), 1000);
//    DonacionDineroRepository.registrar(donacionDinero4);
//
//    // puntaje distribucion viandas : 20
//    DistribucionViandas distribucion4 = DistribucionViandas.nueva(persona, LocalDate.nueva(2024, 5, 2).atStartOfDay(), 20);
//    DistribucionViandasRepository.registrar(distribucion4);
//
//    // puntaje heladeras : 3((6-3)+(6-1)+(6-5))5 - 2((5-3)+(5-1))5 = 135 - 60 = 75
//    Heladera heladera4 = Heladera.paraColaborador(10);
//    heladera4.setEstado(EstadoHeladera.ACTIVA);
//    heladera4.setInicioFuncionamiento(LocalDate.nueva(2024, 5, 8));
//    HacerseCargoHeladera hacerseCargoHeladera4 = HacerseCargoHeladera.nueva(persona, heladera4);
//    HacerseCargoHeladeraRepository.registrar(hacerseCargoHeladera4);
//    // puntaje total julio antes tercer canjeo: 4940.0 + 595.0 = 5535.0
//
//    OfertaDeProductos ofertaDeProductos3 = OfertaDeProductos.nueva(otraPersona, "pluma", 535.0); //en vez nueva calcularlo a mano capaz mejor que haga la cuenta la compu, pero no se
//    CanjeDePuntos tercerCanjeo = CanjeDePuntos.nueva(persona, LocalDate.nueva(2024, 6, 20).atStartOfDay(), 535.0, 5000.0, ofertaDeProductos3);
//    canjeDePuntosRepository.registrar(tercerCanjeo);
//
//    // heladeras : 3((7-3)+(7-1)+(7-5))5 - 3((6-3)+(6-1)+(6-5))5 = 180 - 135 = 45
//    PuntosPorColaboracion calculadorDePuntos3 = PuntosPorColaboracion.nueva(persona);
//    Assertions.assertEquals(5045.0, calculadorDePuntos3.calcularPuntos());
//  }

}



