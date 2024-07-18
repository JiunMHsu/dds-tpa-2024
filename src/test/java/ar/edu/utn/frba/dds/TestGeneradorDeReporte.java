package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.reportes.GeneradorDeReporte;
import ar.edu.utn.frba.dds.reportes.RegistroDonacion;
import ar.edu.utn.frba.dds.reportes.RegistroIncidente;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import com.aspose.pdf.Document;
import com.aspose.pdf.TextAbsorber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGeneradorDeReporte {

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    inicializarDatosDePrueba();
  }

  private void inicializarDatosDePrueba() {
    // Datos de prueba
    RegistroIncidente.incidentesPorHeladera = new HashMap<>();
    RegistroIncidente.incidentesPorHeladera.put("Heladera1", 5);
    RegistroIncidente.incidentesPorHeladera.put("Heladera2", 3);

    RegistroMovimiento.viandasQuitadas = new HashMap<>();
    RegistroMovimiento.viandasQuitadas.put("Heladera1", 10);
    RegistroMovimiento.viandasQuitadas.put("Heladera2", 8);

    RegistroMovimiento.viandasAgregadas = new HashMap<>();
    RegistroMovimiento.viandasAgregadas.put("Heladera1", 15);
    RegistroMovimiento.viandasAgregadas.put("Heladera2", 12);

    RegistroDonacion.viandasPorColaborador = new HashMap<>();
    RegistroDonacion.viandasPorColaborador.put("Colaborador1", 20);
    RegistroDonacion.viandasPorColaborador.put("Colaborador2", 18);
  }

  @Test
  public void testGeneradorDeReporte() {

    GeneradorDeReporte.generadorDeReporte();


    String[] pdfFilePaths = {
        "Cantidad_de_Fallas_por_Heladera.pdf",
        "Cantidad_de_Viandas_Retiradas_por_Heladera.pdf",
        "Cantidad_de_Viandas_Agregadas_por_Heladera.pdf",
        "Cantidad_de_Viandas_por_Colaborador.pdf"
    };

    String[] expectedTitles = {
        "Cantidad de Fallas por Heladera",
        "Cantidad de Viandas Retiradas por Heladera",
        "Cantidad de Viandas Agregadas por Heladera",
        "Cantidad de Viandas por Colaborador"
    };

    Map<String, Integer>[] expectedData = new Map[]{
        RegistroIncidente.incidentesPorHeladera,
        RegistroMovimiento.viandasQuitadas,
        RegistroMovimiento.viandasAgregadas,
        RegistroDonacion.viandasPorColaborador
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
        String expectedText = nombre + " = " + cantidad;
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
