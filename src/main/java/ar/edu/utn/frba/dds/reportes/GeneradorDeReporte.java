package ar.edu.utn.frba.dds.reportes;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.HtmlFragment;
import com.aspose.pdf.Page;
import com.aspose.pdf.Paragraphs;
import com.aspose.pdf.TextFragment;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.io.File;
import java.util.Date;

public class GeneradorDeReporte {

  private static GeneradorDeReporte instancia = null;
  private static final String CARPETA_REPORTES = "C:\\Diseno de Sistemas\\2024-tpa-ma-ma-grupo-22\\doc\\archivos";
  private GeneradorDeReporte(){
    generarReporteSemanal();
  }
  public static GeneradorDeReporte obtenerInstancia(){
    if(instancia == null){
      instancia = new GeneradorDeReporte();
    }
    return instancia;
  }

  public static void generarReporteSemanal() {

    Map<String, Integer> incidentesPorHeladera = RegistroIncidente.incidentesPorHeladera();
    Map<String, Integer> donacionPorColaborador = RegistroDonacion.donacionesPorColaborador();
    Map<String, Integer> viandasAgregadas = RegistroMovimiento.getViandasAgregadas();
    Map<String, Integer> viandasQuitadas = RegistroMovimiento.getViandasQuitadas();

    crearPDF("Cantidad de Fallas por Heladera", "FallasPorHeladera", incidentesPorHeladera);
    crearPDF("Cantidad de Viandas por Colaborador", "ViandasPorColaborador", donacionPorColaborador);
    crearPDFCombinado("Cantidad de Viandas Retiradas/Colocadas por Heladera", "ViandasRetiradasOColocadasPorHeladera", viandasQuitadas, viandasAgregadas);

    RegistroMovimiento.vaciarHashMap();

    System.out.println("Reportes generados correctamente.");
  }

  public static void crearPDF(String titulo, String tipo, Map<String, Integer> datos) {
    try {
      Document pdfDocument = new Document();
      Page page = pdfDocument.getPages().add();

      agregarTitulo(page, titulo);
      agregarDatos(page, datos);

      File carpeta = new File(CARPETA_REPORTES);
      if (!carpeta.exists()) {
        carpeta.mkdirs(); //por las dudas
      }
      String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      pdfDocument.save(CARPETA_REPORTES + "\\" + fechaActual + "-" + tipo + ".pdf");
    } catch (RuntimeException e) {
      System.out.println("Error al crear el documento.");
    }
  }

  public static void crearPDFCombinado(String titulo, String tipo, Map<String, Integer> datos1, Map<String, Integer> datos2) {
    try {
      Document pdfDocument = new Document();
      Page page = pdfDocument.getPages().add();

      agregarTitulo(page, titulo);
      agregarTitulo(page, "Cantidad de Viandas Retiradas por Heladera");
      agregarDatos(page,  datos1);
      agregarTitulo(page,"Cantidad de Viandas Agregadas por Heladera" );
      agregarDatos(page, datos2);

      String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      pdfDocument.save(CARPETA_REPORTES + "\\" + fechaActual + "-" + tipo + ".pdf");
    } catch (RuntimeException e) {
      System.out.println("Error al crear el documento.");
    }
  }

  private static void agregarTitulo(Page page, String titulo) {
    Paragraphs textFragments = page.getParagraphs();
    TextFragment textFragment = new TextFragment(titulo);
    textFragment.getTextState().setFontSize(18);
    textFragment.getTextState().setFont(FontRepository.findFont("Arial"));
    textFragment.getTextState().setForegroundColor(Color.getBlack());
    textFragments.add(textFragment);
  }

  private static void agregarDatos(Page page, Map<String, Integer> datos) {
    StringBuilder htmlContent = new StringBuilder();
    htmlContent.append("<table style='width: 100%; font-family: Times New Roman; color: black;'>");
    for (Map.Entry<String, Integer> entry : datos.entrySet()) {
      htmlContent.append("<tr><td>").append(entry.getKey()).append("</td><td>").append(entry.getValue()).append("</td></tr>");
    }
    htmlContent.append("</table>");
    HtmlFragment htmlFragment = new HtmlFragment(htmlContent.toString());
    page.getParagraphs().add(htmlFragment);
  }
}
