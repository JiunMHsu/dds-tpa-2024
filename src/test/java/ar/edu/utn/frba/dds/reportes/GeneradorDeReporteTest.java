package ar.edu.utn.frba.dds.reportes;

public class GeneradorDeReporteTest {

    //TODO hacerlo de nuevo
//    Map<String, Integer> incidentesPorHeladera;
//    Map<String, Integer> viandasPorColaborador;
//
//    Map<String, Integer> viandasAgregadas;
//    Map<String, Integer> viandasQuitadas;
//
//    RegistroMovimiento registroMovimiento;
//    RegistroIncidente registroIncidente;
//    RegistroDonacion registroDonacion;
//
//    @BeforeEach
//    public void setUp() {
//
//        incidentesPorHeladera = new HashMap<>();
//        incidentesPorHeladera.put("Heladera1", 5);
//        incidentesPorHeladera.put("Heladera2", 3);
//
//        viandasAgregadas = new HashMap<>();
//        viandasAgregadas.put("Heladera1", 15);
//        viandasAgregadas.put("Heladera2", 12);
//
//        viandasQuitadas = new HashMap<>();
//        viandasQuitadas.put("Heladera1", 10);
//        viandasQuitadas.put("Heladera2", 8);
//
//        viandasPorColaborador = new HashMap<>();
//        viandasPorColaborador.put("Colaborador1", 20);
//        viandasPorColaborador.put("Colaborador2", 18);
//
//        registroMovimiento = mock(RegistroMovimiento.class);
//        when(registroMovimiento.getViandasAgregadas()).thenReturn(viandasAgregadas);
//        when(registroMovimiento.getViandasQuitadas()).thenReturn(viandasQuitadas);
//
//        registroIncidente = mock(RegistroIncidente.class);
//        when(registroIncidente.incidentesPorHeladera()).thenReturn(incidentesPorHeladera);
//
//        registroDonacion = mock(RegistroDonacion.class);
//        when(registroDonacion.donacionesPorColaborador()).thenReturn(viandasPorColaborador);
//    }
//
//    @Test
//    @DisplayName("Se puden generar los pdf a partir de diccionarios")
//    public void testGeneracionDeReporte() {
//        GeneradorDeReporte generador = GeneradorDeReporte.de(registroIncidente, registroDonacion, registroMovimiento);
//        generador.generarReporteSemanal();
//    }
}
