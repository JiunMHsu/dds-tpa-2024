package ar.edu.utn.frba.dds.utils;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlFragment;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PDFGenerator implements IPDFGenerator {

    private final String dir;

    public PDFGenerator(String dir) {
        this.dir = dir;
    }

    @Override
    public String generateDocument(String title, Map<String, Integer> data) {
        String path = null;

        try (Document pdfDocument = new Document()) {
            Page page = pdfDocument.getPages().add();

            writeTitle(page, title);
            System.out.println("Creating PDF...");
            writeData(page, data);

            path = saveDocument(pdfDocument, title);
        } catch (RuntimeException e) {
            // TODO - Ver excepciones mas robustas
            System.out.println("Error creating document.");
        }

        return path;
    }

    private void writeTitle(Page page, String title) {
        TextFragment textFragment = new TextFragment(title);
        textFragment.getTextState().setFontSize(18);
        textFragment.getTextState().setForegroundColor(Color.getBlack());
        page.getParagraphs().add(textFragment);
    }

    private void writeData(Page page, Map<String, Integer> data) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<br>");

        htmlContent.append("<table style='width: 100%; font-family: Monospaced; color: black;'>");
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            htmlContent
                    .append("<tr><td>")
                    .append(entry.getKey())
                    .append("</td><td>")
                    .append(entry.getValue())
                    .append("</td></tr>");
        }

        htmlContent.append("</table>");

        HtmlFragment htmlFragment = new HtmlFragment(htmlContent.toString());
        page.getParagraphs().add(htmlFragment);
    }

    // TODO - Revisar
    private String saveDocument(Document document, String name) {
        Path documentsPath = Paths.get(dir);
        System.out.println("Saving Document..." + documentsPath.toAbsolutePath());
        File folder = documentsPath.toFile();

        if (folder.exists() || folder.mkdirs()) {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            Path filePath = Paths.get(dir, currentDate + "-" + name + ".pdf");
            System.out.println("File path: " + filePath.toAbsolutePath());

            document.save(filePath.toAbsolutePath().toString());

            return documentsPath.relativize(filePath).toString();
        }

        return null;
    }

//    public void crearPDFCombinado(String titulo, String tipo, Map<String, Integer> datos1, Map<String, Integer> datos2) {
//        try (Document pdfDocument = new Document()) {
//            Page page = pdfDocument.getPages().add();
//            System.out.println("Creando PDF...");
//
//            agregarTitulo(page, titulo);
//            agregarTitulo(page, "Cantidad por Viandas Retiradas por Heladera");
//            agregarDatos(page, datos1);
//            agregarTitulo(page, "Cantidad por Viandas Agregadas por Heladera");
//            agregarDatos(page, datos2);
//
//            //todo path puede ser null
//            String path = guardarDocumento(pdfDocument, tipo);
//
//            Reporte nuevoReporte = ServiceLocator.instanceOf(Reporte.class);
//            nuevoReporte.setFecha(LocalDate.now());
//            nuevoReporte.setTitulo(pdfDocument.getFileName());
//            nuevoReporte.setPath(path);
//
//            reporteRepository.guardar(nuevoReporte);
//
//        } catch (RuntimeException e) {
//            System.out.println("Error al crear el documento.");
//        }
//    }
}
