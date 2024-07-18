package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import ar.edu.utn.frba.dds.reportes.RegistroDonacion;
import ar.edu.utn.frba.dds.reportes.RegistroIncidente;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import com.aspose.pdf.Document;
import com.aspose.pdf.TextAbsorber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    String path = "Cantidad_de_Fallas_por_Heladera.pdf";
    GeneradorDeReporte.crearPDF(title, incidentesPorHeladera);

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
  @DisplayName("")
  public void testGeneradorDeReporte() {

    GeneradorDeReporte.crearPDF("Cantidad de Fallas por Heladera", incidentesPorHeladera);
    GeneradorDeReporte.crearPDF("Cantidad de Viandas Retiradas por Heladera", viandasQuitadas);
    GeneradorDeReporte.crearPDF("Cantidad de Viandas Agregadas por Heladera", viandasAgregadas);
    GeneradorDeReporte.crearPDF("Cantidad de Viandas por Colaborador", viandasPorColaborador);

    String[] pdfFilePaths = {
        "Cantidad_de_Fallas_por_Heladera.pdf",
        "Cantidad_de_Viandas_por_Colaborador.pdf",
        "Cantidad_de_Viandas_Agregadas_por_Heladera.pdf",
        "Cantidad_de_Viandas_Retiradas_por_Heladera.pdf"
    };

    String[] expectedTitles = {
        "Cantidad de Fallas por Heladera",
        "Cantidad de Viandas por Colaborador",
        "Cantidad de Viandas Agregadas por Heladera",
        "Cantidad de Viandas Retiradas por Heladera"
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
