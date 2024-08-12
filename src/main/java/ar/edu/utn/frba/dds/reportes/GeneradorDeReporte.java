package ar.edu.utn.frba.dds.reportes;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.Paragraphs;
import com.aspose.pdf.TextFragment;
import java.util.Map;

public class GeneradorDeReporte {

  public static void main(String[] args) {
    GeneradorDeReporte.generarReporteSemanal();
  }

  public static void generarReporteSemanal() {

    Map<String, Integer> incidentesPorHeladera = RegistroIncidente.incidentesPorHeladera();
    Map<String, Integer> donacionPorColaborador = RegistroDonacion.donacionesPorColaborador();
    Map<String, Integer> viandasAgregadas = RegistroMovimiento.getViandasAgregadas();
    Map<String, Integer> viandasQuitadas = RegistroMovimiento.getViandasQuitadas();

    crearPDF("Cantidad de Fallas por Heladera", incidentesPorHeladera);
    crearPDF("Cantidad de Viandas por Colaborador", donacionPorColaborador);

    // Hace uno solo
    crearPDF("Cantidad de Viandas Retiradas por Heladera", viandasQuitadas);
    crearPDF("Cantidad de Viandas Agregadas por Heladera", viandasAgregadas);

    RegistroMovimiento.vaciarHashMap();

    System.out.println("Reportes generados correctamente.");
  }

  public static void crearPDF(String titulo, Map<String, Integer> datos) {
    try {
      Document pdfDocument = new Document();
      Page page = pdfDocument.getPages().add();

      agregarTitulo(page, titulo);
      agregarDatos(page, datos);

      pdfDocument.save(titulo.replace(" ", "_") + ".pdf");
    } catch (RuntimeException e) {
      System.out.println("Error al crear el documento.");
    }
  }

  private static void agregarTitulo(Page page, String titulo) {
    Paragraphs textFragments = page.getParagraphs();
    TextFragment textFragment = new TextFragment(titulo);
    textFragment.getTextState().setFontSize(14);
    textFragments.add(textFragment);
  }

  private static void agregarDatos(Page page, Map<String, Integer> datos) {
    Paragraphs textFragments = page.getParagraphs();

    for (Map.Entry<String, Integer> entry : datos.entrySet()) {
      String nombre = entry.getKey();
      Integer cantidad = entry.getValue();
      TextFragment textFragment = new TextFragment(nombre + " : " + cantidad);
      textFragment.getTextState().setFontSize(12);
      textFragments.add(textFragment);
    }
  }
}
