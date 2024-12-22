package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.utils.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GeneradorDeReporteTest {

  PDFGenerator pdfGenerator;

  Map<String, Integer> incidentesPorHeladera;
  Map<String, Integer> viandasPorColaborador;

  Map<String, Integer> viandasAgregadas;
  Map<String, Integer> viandasQuitadas;

  @BeforeEach
  public void setUp() {

    pdfGenerator = new PDFGenerator(AppProperties.getInstance().propertyFromName("REPORT_DIR"));

    incidentesPorHeladera = new HashMap<>();
    incidentesPorHeladera.put("Heladera1", 5);
    incidentesPorHeladera.put("Heladera2", 3);

    viandasAgregadas = new HashMap<>();
    viandasAgregadas.put("Heladera1", 15);
    viandasAgregadas.put("Heladera2", 12);

    viandasQuitadas = new HashMap<>();
    viandasQuitadas.put("Heladera1", 10);
    viandasQuitadas.put("Heladera2", 8);

    viandasPorColaborador = new HashMap<>();
    viandasPorColaborador.put("Colaborador1", 20);
    viandasPorColaborador.put("Colaborador2", 18);
  }

  @Test
  @DisplayName("Se puden generar los pdf a partir por diccionarios")
  public void testGeneracionDeReporte() {
    pdfGenerator.generateDocument("Donacion Viandas Por Colaborador", viandasPorColaborador);
  }
}
