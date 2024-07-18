package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.PageCollection;
import com.aspose.pdf.Paragraphs;
import com.aspose.pdf.TextFragment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestGeneradorDeReporte {
  Document mockDocument;
  PageCollection mockPageCollection;
  Page mockPage;
  Paragraphs mockParagraphs;

  @BeforeEach
  public void setUp() {
    mockDocument = mock(Document.class);
    mockPageCollection = mock(PageCollection.class);
    mockPage = mock(Page.class);
    mockParagraphs = mock(Paragraphs.class);

    when(mockDocument.getPages()).thenReturn(mockPageCollection);
    when(mockPageCollection.add()).thenReturn(mockPage);
    when(mockPage.getParagraphs()).thenReturn(mockParagraphs);

    Map<String, Integer> incidentesPorHeladera = new HashMap<>();
    incidentesPorHeladera.put("Heladera1", 5);
    incidentesPorHeladera.put("Heladera2", 3);

    Map<String, Integer> viandasQuitadas = new HashMap<>();
    viandasQuitadas.put("Heladera3", 10);
    viandasQuitadas.put("Heladera4", 7);

    Map<String, Integer> viandasAgregadas = new HashMap<>();
    viandasAgregadas.put("Heladera5", 15);
    viandasAgregadas.put("Heladera6", 12);

    Map<String, Integer> viandasPorColaborador = new HashMap<>();
    viandasPorColaborador.put("Colaborador1", 20);
    viandasPorColaborador.put("Colaborador2", 18);
  }

  @Test
  @DisplayName("Reporte en pdf")
  public void testGeneradorDeReporte() {

    GeneradorDeReporte.generadorDeReporte();
    verify(mockParagraphs, times(4)).add(any(TextFragment.class));
    verify(mockDocument).save("Reporte.pdf");
  }
}
