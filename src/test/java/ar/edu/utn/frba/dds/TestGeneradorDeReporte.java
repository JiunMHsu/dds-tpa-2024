package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextAbsorber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGeneradorDeReporte {

  @Mock
  private Document mockDocument;
  @Mock
  private Page mockPage;

  private GeneradorDeReporte reporte;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    reporte = spy(new GeneradorDeReporte());

    doReturn(mockDocument).when(reporte).crearPDF(anyString(), anyMap());
    when(mockDocument.getPages().add()).thenReturn(mockPage);
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

    // Generar los reportes y guardar los PDFs en archivos temporales
    String[] pdfFilePaths = {
        "reporte_test_fallas.pdf",
        "reporte_test_viandas_retiradas.pdf",
        "reporte_test_viandas_agregadas.pdf",
        "reporte_test_viandas_colaborador.pdf"
    };

    reporte.generadorDeReporte();

    mockDocument.save(pdfFilePaths[0]);
    mockDocument.save(pdfFilePaths[1]);
    mockDocument.save(pdfFilePaths[2]);
    mockDocument.save(pdfFilePaths[3]);

    String[] expectedContents = {
        "Cantidad de Fallas por Heladera",
        "Cantidad de Viandas Retiradas por Heladera",
        "Cantidad de Viandas Agregadas por Heladera",
        "Cantidad de Viandas por Colaborador"
    };

    for (int i = 0; i < pdfFilePaths.length; i++) {
      File pdfFile = new File(pdfFilePaths[i]);
      assertTrue(pdfFile.exists());

      Document pdfDocument = new Document(pdfFilePaths[i]);

      // para extraer el texto de la primera página
      TextAbsorber textAbsorber = new TextAbsorber();
      pdfDocument.getPages().accept(textAbsorber);
      String pageText = textAbsorber.getText();

      assertTrue(pageText.contains(expectedContents[i]));

      try {
        Files.delete(pdfFile.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
