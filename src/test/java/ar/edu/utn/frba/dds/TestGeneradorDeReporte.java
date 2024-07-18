package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReporteTest {

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
  public void testGeneradorDeReporte() throws IOException {
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

    reporte.setIncidentesPorHeladera(incidentesPorHeladera);
    reporte.setViandasQuitadas(viandasQuitadas);
    reporte.setViandasAgregadas(viandasAgregadas);
    reporte.setViandasPorColaborador(viandasPorColaborador);

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

    for (String pdfFilePath : pdfFilePaths) {
      File pdfFile = new File(pdfFilePath);
      assertTrue(pdfFile.exists());

      // verificar el contenido del PDF
      Document pdfDocument = new Document(pdfFilePath);
      Page firstPage = pdfDocument.getPages().get_Item(1);

      // Verificar que el contenido de la primera página contiene el texto esperado
      boolean contieneTextoEsperado = false;
      for (TextFragment fragment : firstPage.getParagraphs().getTextFragments()) {
        if (fragment.getText().contains("Cantidad de Fallas por Heladera") ||
            fragment.getText().contains("Cantidad de Viandas Retiradas por Heladera") ||
            fragment.getText().contains("Cantidad de Viandas Agregadas por Heladera") ||
            fragment.getText().contains("Cantidad de Viandas por Colaborador")) {
          contieneTextoEsperado = true;
          break;
        }
      }

      assertTrue(contieneTextoEsperado);

      Files.delete(pdfFile.toPath());
    }
  }
}
