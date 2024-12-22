package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.utils.IPDFGenerator;
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
  public String generateDocumentWithSections(String title, Map<String, Map<String, Integer>> data) {
    Document pdfDocument = new Document();
    System.out.println("Creating PDF...");

    Page page = pdfDocument.getPages().add();
    writeTitle(page, title);

    for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
      String sectionTitle = entry.getKey();
      Map<String, Integer> sectionData = entry.getValue();
      writeSubTitle(page, sectionTitle);
      writeData(page, sectionData);
    }

    String path = saveDocument(pdfDocument, title);
    System.out.println("Document created successfully. Relative path: " + path);
    return path;
  }

  @Override
  public String generateDocument(String title, Map<String, Integer> data) {
    Document pdfDocument = new Document();
    System.out.println("Creating PDF...");

    Page page = pdfDocument.getPages().add();
    writeTitle(page, title);
    writeData(page, data);

    String path = saveDocument(pdfDocument, title);
    System.out.println("Document created successfully. Relative path: " + path);
    return path;
  }

  private void writeTitle(Page page, String title) {
    TextFragment textFragment = new TextFragment(title);
    textFragment.getTextState().setFontSize(18);
    textFragment.getTextState().setForegroundColor(Color.getBlack());
    page.getParagraphs().add(textFragment);
  }

  private void writeSubTitle(Page page, String title) {
    TextFragment textFragment = new TextFragment(title);
    textFragment.getTextState().setFontSize(14);
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

  private String saveDocument(Document document, String name) {
    Path documentsPath = Paths.get(dir);
    System.out.println("Saving Document in: " + documentsPath.toAbsolutePath());
    File folder = documentsPath.toFile();

    if (folder.exists() || folder.mkdirs()) {
      String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      String nameWithoutSpaces = name.toLowerCase().replaceAll("\\s", "_");

      Path filePath = Paths.get(dir, currentDate + "-" + nameWithoutSpaces + ".pdf");
      System.out.println("File path: " + filePath.toAbsolutePath());

      document.save(filePath.toAbsolutePath().toString());
      return documentsPath.relativize(filePath).toString();
    }

    return null;
  }
}
