package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import com.aspose.pdf.Document;
import com.aspose.pdf.TextAbsorber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGeneradorDeReporte {

  Map<String, Integer> incidentesPorHeladera;
  Map<String, Integer> viandasPorColaborador;

  Map<String, Integer> viandasAgregadas;
  Map<String, Integer> viandasQuitadas;


  @BeforeEach
  public void setUp() {

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
  @DisplayName("Se puden generar los pdf a partir de diccionarios")
  public void testGeneracionDeReporte() {
    String title = "Cantidad de Fallas por Heladera";
    String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String tipo = "FallasPorHeladera";
    String path = "C:\\Diseno de Sistemas\\2024-tpa-ma-ma-grupo-22\\doc\\archivos\\" + fechaActual + "-" + tipo + ".pdf";

    GeneradorDeReporte.crearPDF(title,tipo, incidentesPorHeladera);

    File pdfFile = new File(path);
    assertTrue(pdfFile.exists());

    Document pdfDocument = new Document(path);

    TextAbsorber textAbsorber = new TextAbsorber();
    pdfDocument.getPages().accept(textAbsorber);
    String pageText = textAbsorber.getText();

    // Verificar el título
    assertTrue(pageText.contains("Cantidad de Fallas por Heladera"));

    // Verificar los datos
    for (Map.Entry<String, Integer> entry : incidentesPorHeladera.entrySet()) {
      String nombre = entry.getKey();
      Integer cantidad = entry.getValue();
      String expectedText = nombre + " : " + cantidad;
      System.out.println("Verificando: " + expectedText + " en " + path); // Añadir esta línea para depuración
      assertTrue(pageText.contains(expectedText), "El dato esperado (" + expectedText + ") no se encuentra en el PDF " + path);
    }

    try {
      Files.delete(pdfFile.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Generacion de multiples reportes")
  public void testGeneradorDeReporte() {
    String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    GeneradorDeReporte.crearPDF("Cantidad de Fallas por Heladera", "FallasPorHeladera", incidentesPorHeladera);
    GeneradorDeReporte.crearPDFCombinado("Cantidad de Viandas Retiradas/Colocadas por Heladera","ViandasretiradasOColocadasPorHeladera", viandasQuitadas, viandasAgregadas);
    GeneradorDeReporte.crearPDF("Cantidad de Viandas por Colaborador", "ViandasPorColaborador", viandasPorColaborador);

    String[] pdfFilePaths = {
        "C:\\Diseno de Sistemas\\2024-tpa-ma-ma-grupo-22\\doc\\archivos\\" + fechaActual + "-FallasPorHeladera.pdf",
        "C:\\Diseno de Sistemas\\2024-tpa-ma-ma-grupo-22\\doc\\archivos\\" + fechaActual + "-ViandasPorColaborador.pdf",
        "C:\\Diseno de Sistemas\\2024-tpa-ma-ma-grupo-22\\doc\\archivos\\" + fechaActual + "-ViandasRetiradasOColocadasPorHeladera.pdf"
    };

    String[] expectedTitles = {
        "Cantidad de Fallas por Heladera",
        "Cantidad de Viandas por Colaborador",
        "Cantidad de Viandas Retiradas/Colocadas por Heladera"
    };

    Map<String, Integer>[] expectedData = new Map[]{
        incidentesPorHeladera,
        viandasPorColaborador,
        viandasAgregadas,
        viandasQuitadas
    };

    for (int i = 0; i < pdfFilePaths.length; i++) {
      File pdfFile = new File(pdfFilePaths[i]);
      assertTrue(pdfFile.exists(), "El archivo " + pdfFilePaths[i] + " no fue creado.");

      Document pdfDocument = new Document(pdfFilePaths[i]);

      TextAbsorber textAbsorber = new TextAbsorber();
      pdfDocument.getPages().accept(textAbsorber);
      String pageText = textAbsorber.getText();

      // Verificar el título
      assertTrue(pageText.contains(expectedTitles[i]), "El título esperado no se encuentra en el PDF " + pdfFilePaths[i]);

      // Verificar los datos
      for (Map.Entry<String, Integer> entry : expectedData[i].entrySet()) {
        String nombre = entry.getKey();
        Integer cantidad = entry.getValue();
        String expectedText = nombre + " : " + cantidad;
        System.out.println("Verificando: " + expectedText + " en " + pdfFilePaths[i]); // Añadir esta línea para depuración
        assertTrue(pageText.contains(expectedText), "El dato esperado (" + expectedText + ") no se encuentra en el PDF " + pdfFilePaths[i]);
      }

      try {
        Files.delete(pdfFile.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
