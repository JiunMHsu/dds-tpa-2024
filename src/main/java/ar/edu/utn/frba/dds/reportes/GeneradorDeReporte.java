package ar.edu.utn.frba.dds.reportes;

import static ar.edu.utn.frba.dds.reportes.RegistroDonacion.viandasPorColaborador;
import static ar.edu.utn.frba.dds.reportes.RegistroMovimiento.viandasAgregadas;
import static ar.edu.utn.frba.dds.reportes.RegistroMovimiento.viandasQuitadas;
import static ar.edu.utn.frba.dds.reportes.RegistroIncidente.incidentesPorHeladera;


import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.Paragraphs;
import com.aspose.pdf.TextFragment;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GeneradorDeReporte {

    public static void generadorDeReporte(){

      Document pdfDocument = new Document();
      Page page = pdfDocument.getPages().add();

      agregarTitulo(page, "Cantidad de Fallas por Heladera");
      agregarDatos(page, incidentesPorHeladera);

      agregarTitulo(page, "Cantidad de Viandas Retiradas por Heladera");
      agregarDatos(page, viandasQuitadas);

      agregarTitulo(page, "Cantidad de Viandas Agregadas por Heladera");
      agregarDatos(page, viandasAgregadas);

      agregarTitulo(page, "Cantidad de Viandas por Colaborador");
      agregarDatos(page, viandasPorColaborador);

      // Guardar el PDF
      pdfDocument.save("Reporte.pdf");
      System.out.println("Reporte generado correctamente.");
    }

    private static void agregarTitulo(Page page, String titulo) {
      Paragraphs textFragments = page.getParagraphs();
      TextFragment textFragment = new TextFragment(titulo);
      textFragment.getTextState().setFontSize(14);
      textFragments.add(textFragment);
    }

    private static void agregarDatos(Page page, Map<String, Integer> datos) {
      for (String nombre : datos.keySet()) {
        Paragraphs textFragments = page.getParagraphs();
        TextFragment textFragment = new TextFragment(nombre + ": " + datos.get(nombre));
        textFragment.getTextState().setFontSize(12);
        textFragments.add(textFragment);
      }
    }
  public static void iniciarActualizacionSemanal(){
    Timer timer = new Timer();
    TimerTask tareaSemanal = new TimerTask(){
      @Override
      public void run(){
        generadorDeReporte();
      }
    };
    timer.scheduleAtFixedRate(tareaSemanal, 0, 604800000);
  }
}

