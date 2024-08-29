package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.AppConfig;
import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.HtmlFragment;
import com.aspose.pdf.Page;
import com.aspose.pdf.Paragraphs;
import com.aspose.pdf.TextFragment;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Builder;

@Builder
public class GeneradorDeReporte {

  private final String directorioReportes;
  private final RegistroIncidente registroIncidente;
  private final RegistroDonacion registroDonacion;
  private final RegistroMovimiento registroMovimiento;
  private final ScheduledExecutorService planificador;

  public static GeneradorDeReporte de(RegistroIncidente registroIncidente,
                                      RegistroDonacion registroDonacion,
                                      RegistroMovimiento registroMovimiento,
                                      String directorio) {

    return GeneradorDeReporte
        .builder()
        .registroIncidente(registroIncidente)
        .registroDonacion(registroDonacion)
        .registroMovimiento(registroMovimiento)
        .directorioReportes(directorio)
        .planificador(Executors.newScheduledThreadPool(1))
        .build();
  }

  public static GeneradorDeReporte de(RegistroIncidente registroIncidente,
                                      RegistroDonacion registroDonacion,
                                      RegistroMovimiento registroMovimiento) {

    return GeneradorDeReporte
        .builder()
        .registroIncidente(registroIncidente)
        .registroDonacion(registroDonacion)
        .registroMovimiento(registroMovimiento)
        .directorioReportes(AppConfig.getProperty("REPORT_DIR"))
        .planificador(Executors.newScheduledThreadPool(1))
        .build();
  }

  public void planificar(int frecuencia, TimeUnit unidadDeFrecuencia) {
    planificador.scheduleAtFixedRate(this::generarReporteSemanal, 0, frecuencia, unidadDeFrecuencia);
  }

  public void generarReporteSemanal() {

    LocalDate haceUnaSemana = LocalDate.now().minusWeeks(1);
    Map<String, Integer> incidentesPorHeladera = registroIncidente.incidentesPorHeladera(haceUnaSemana);
    Map<String, Integer> donacionPorColaborador = registroDonacion.donacionesPorColaborador(haceUnaSemana);
    Map<String, Integer> viandasAgregadas = registroMovimiento.getViandasAgregadas();
    Map<String, Integer> viandasQuitadas = registroMovimiento.getViandasQuitadas();

    crearPDF("Fallas de Heladera", "FallasDeHeladeras", incidentesPorHeladera);
    crearPDF("Viandas Donadas por Colaborador", "ViandasDonadas", donacionPorColaborador);
    crearPDFCombinado("Cantidad de Viandas Retiradas/Colocadas", "MovimientoDeViandas", viandasQuitadas, viandasAgregadas);

    registroMovimiento.vaciarRegistro();

    System.out.println("Reportes generados correctamente.");
  }

  public void crearPDF(String titulo, String tipo, Map<String, Integer> datos) {
    try (Document pdfDocument = new Document()) {

      Page page = pdfDocument.getPages().add();
      agregarTitulo(page, titulo);
      agregarDatos(page, datos);

      guardarDocumento(pdfDocument, tipo);

    } catch (RuntimeException e) {
      System.out.println("Error al crear el documento.");
    }
  }

  public void crearPDFCombinado(String titulo, String tipo, Map<String, Integer> datos1, Map<String, Integer> datos2) {
    try (Document pdfDocument = new Document()) {

      Page page = pdfDocument.getPages().add();

      agregarTitulo(page, titulo);
      agregarTitulo(page, "Cantidad de Viandas Retiradas por Heladera");
      agregarDatos(page, datos1);
      agregarTitulo(page, "Cantidad de Viandas Agregadas por Heladera");
      agregarDatos(page, datos2);

      guardarDocumento(pdfDocument, tipo);

    } catch (RuntimeException e) {
      System.out.println("Error al crear el documento.");
    }
  }

  private void guardarDocumento(Document documento, String nombre) {
    Path rutaReportes = Paths.get(directorioReportes);

    File carpeta = rutaReportes.toFile();
    if (carpeta.exists() || carpeta.mkdirs()) {
      String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

      Path rutaArchivo = Paths.get(directorioReportes, fechaActual + "-" + nombre + ".pdf");
      documento.save(rutaArchivo.toAbsolutePath().toString());
    }
  }

  private void agregarTitulo(Page page, String titulo) {
    Paragraphs textFragments = page.getParagraphs();
    TextFragment textFragment = new TextFragment(titulo);
    textFragment.getTextState().setFontSize(18);
    textFragment.getTextState().setFont(FontRepository.findFont("Courier New"));
    textFragment.getTextState().setForegroundColor(Color.getBlack());
    textFragments.add(textFragment);
  }

  private void agregarDatos(Page page, Map<String, Integer> datos) {
    StringBuilder htmlContent = new StringBuilder();
    htmlContent.append("<table style='width: 100%; font-family: Courier New; color: black;'>");
    for (Map.Entry<String, Integer> entry : datos.entrySet()) {
      htmlContent.append("<tr><td>").append(entry.getKey()).append("</td><td>").append(entry.getValue()).append("</td></tr>");
    }
    htmlContent.append("</table>");
    HtmlFragment htmlFragment = new HtmlFragment(htmlContent.toString());
    page.getParagraphs().add(htmlFragment);
  }
}
