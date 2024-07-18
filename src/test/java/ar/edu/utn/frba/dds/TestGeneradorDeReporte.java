package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import com.aspose.pdf.Document;
import com.aspose.pdf.TextAbsorber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TestGeneradorDeReporte {

  private GeneradorDeReporte reporte;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    reporte = spy(new GeneradorDeReporte());
  }

  @Test
  public void testGeneradorDeReporte() {
    // Datos de prueba
    Map<String, Integer> incidentesPorHeladera = new HashMap<>();
    incidentesPorHeladera.put("Heladera1", 5);
    incidentesPorHeladera.put("Heladera2", 3);

    Map<String, Integer> viandasQuitadas = new HashMap<>();
    viandasQuitadas.put("Heladera1", 10);
    viandasQuitadas.put("Heladera2", 8);

    Map<String, Integer> viandasAgregadas = new HashMap<>();
    viandasAgregadas.put("Heladera1", 15);
    viandasAgregadas.put("Heladera2", 12);

    Map<String, Integer> viandasPorColaborador = new HashMap<>();
    viandasPorColaborador.put("Colaborador1", 20);
    viandasPorColaborador.put("Colaborador2", 18);

    String[] pdfFilePaths = {
        "Cantidad_de_Fallas_por_Heladera.pdf",
        "Cantidad_de_Viandas_Retiradas_por_Heladera.pdf",
        "Cantidad_de_Viandas_Agregadas_por_Heladera.pdf",
        "Cantidad_de_Viandas_por_Colaborador.pdf"
    };

    GeneradorDeReporte.generadorDeReporte();

    //titulos esperados
    String[] expectedTitles = {
        "Cantidad de Fallas por Heladera",
        "Cantidad de Viandas Retiradas por Heladera",
        "Cantidad de Viandas Agregadas por Heladera",
        "Cantidad de Viandas por Colaborador"
    };

    for (int i = 0; i < pdfFilePaths.length; i++) {
      File pdfFile = new File(pdfFilePaths[i]);
      assertTrue(pdfFile.exists(), "El archivo " + pdfFilePaths[i] + " no fue creado.");

      Document pdfDocument = new Document(pdfFilePaths[i]);

      // para tener el texto de la primera página
      TextAbsorber textAbsorber = new TextAbsorber();
      pdfDocument.getPages().accept(textAbsorber);
      String pageText = textAbsorber.getText();

      assertTrue(pageText.contains(expectedTitles[i]), "El título esperado no se encuentra en el PDF " + pdfFilePaths[i]);

      try {
        Files.delete(pdfFile.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
