package ar.edu.utn.frba.dds.services.reporte;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.reporte.ReporteRepository;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import ar.edu.utn.frba.dds.utils.AppProperties;
import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlFragment;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;

@Builder
public class ReporteService implements WithSimplePersistenceUnit {

    private final ReporteRepository reporteRepository;
    private final DonacionViandaRepository donacionViandaRepository;
    private final IncidenteRepository incidenteRepository;
    private final String directorioReportes;

    private final RegistroMovimiento registroMovimiento;

    public static ReporteService de(ReporteRepository reporteRepository,
                                    DonacionViandaRepository donacionViandaRepository,
                                    RegistroMovimiento registroMovimiento,
                                    String directorio) {

        return ReporteService
                .builder()
                .reporteRepository(reporteRepository)
                .donacionViandaRepository(donacionViandaRepository)
                .registroMovimiento(registroMovimiento)
                .directorioReportes(directorio)
                .build();
    }

    public static ReporteService de(ReporteRepository reporteRepository,
                                    DonacionViandaRepository donacionViandaRepository,
                                    RegistroMovimiento registroMovimiento) {

        return ReporteService
                .builder()
                .reporteRepository(reporteRepository)
                .donacionViandaRepository(donacionViandaRepository)
                .registroMovimiento(registroMovimiento)
                .directorioReportes(AppProperties.getInstance().propertyFromName("REPORT_DIR"))
                .build();
    }

    public Map<String, Integer> incidentesPorHeladera() {

        LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
        List<Incidente> incidentes = incidenteRepository.buscarAPartirDe(haceUnaSemana);

        Map<String, Integer> incidentesPorHeladera = new HashMap<>();

        for (Incidente incidente : incidentes) {
            int cantidad = incidentesPorHeladera.getOrDefault(incidente.getHeladera().getNombre(), 0) + 1;
            incidentesPorHeladera.put(incidente.getHeladera().getNombre(), cantidad);
        }

        return incidentesPorHeladera;
    }

    public Map<String, Integer> donacionesPorColaborador() {
        LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
        List<DonacionVianda> donaciones = donacionViandaRepository.obtenerAPartirDe(haceUnaSemana);

        Map<String, Integer> viandasPorColaborador = new HashMap<>();
        for (DonacionVianda donacion : donaciones) {
            // Tampoco es la forma más segura, habría que hacer por email o algo así
            String key = donacion.getColaborador().getNombre() + " " + donacion.getColaborador().getApellido();
            int cantidad = viandasPorColaborador.getOrDefault(key, 0) + 1;
            viandasPorColaborador.put(key, cantidad);
        }

        return viandasPorColaborador;
    }

    public void generarReporteSemanal() {
        Map<String, Integer> incidentesPorHeladera = this.incidentesPorHeladera();
        Map<String, Integer> donacionPorColaborador = this.donacionesPorColaborador();
        Map<String, Integer> viandasAgregadas = registroMovimiento.getViandasAgregadas();
        Map<String, Integer> viandasQuitadas = registroMovimiento.getViandasQuitadas();

        crearPDF("Fallas por Heladera", "FallasDeHeladeras", incidentesPorHeladera);
        crearPDF("Viandas Donadas por Colaborador", "ViandasDonadas", donacionPorColaborador);
        crearPDFCombinado("Cantidad por Viandas Retiradas/Colocadas", "MovimientoDeViandas", viandasQuitadas, viandasAgregadas);


        registroMovimiento.vaciarRegistro();

        System.out.println("Reportes generados correctamente.");
    }

    public void crearPDF(String titulo, String tipo, Map<String, Integer> datos) {
        try (Document pdfDocument = new Document()) {
            Page page = pdfDocument.getPages().add();

            agregarTitulo(page, titulo);
            System.out.println("Creando PDF...");
            agregarDatos(page, datos);
            //todo path puede ser null
            String path = guardarDocumento(pdfDocument, tipo);

            Reporte nuevoReporte = ServiceLocator.instanceOf(Reporte.class);
            nuevoReporte.setFecha(LocalDate.now());
            nuevoReporte.setTitulo(pdfDocument.getFileName());
            nuevoReporte.setPath(path);

            reporteRepository.guardar(nuevoReporte);

        } catch (RuntimeException e) {
            System.out.println("Error al crear el documento.");
        }
    }

    public void crearPDFCombinado(String titulo, String tipo, Map<String, Integer> datos1, Map<String, Integer> datos2) {
        try (Document pdfDocument = new Document()) {
            Page page = pdfDocument.getPages().add();
            System.out.println("Creando PDF...");

            agregarTitulo(page, titulo);
            agregarTitulo(page, "Cantidad por Viandas Retiradas por Heladera");
            agregarDatos(page, datos1);
            agregarTitulo(page, "Cantidad por Viandas Agregadas por Heladera");
            agregarDatos(page, datos2);

            //todo path puede ser null
            String path = guardarDocumento(pdfDocument, tipo);

            Reporte nuevoReporte = ServiceLocator.instanceOf(Reporte.class);
            nuevoReporte.setFecha(LocalDate.now());
            nuevoReporte.setTitulo(pdfDocument.getFileName());
            nuevoReporte.setPath(path);

            reporteRepository.guardar(nuevoReporte);

        } catch (RuntimeException e) {
            System.out.println("Error al crear el documento.");
        }
    }

    private String guardarDocumento(Document documento, String nombre) {
        Path rutaReportes = Paths.get(directorioReportes);
        System.out.println("Guardando Documento..." + rutaReportes.toAbsolutePath());
        File carpeta = rutaReportes.toFile();

        if (carpeta.exists() || carpeta.mkdirs()) {
            String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            Path rutaArchivo = Paths.get(directorioReportes, fechaActual + "-" + nombre + ".pdf");
            System.out.println("Ruta del archivo: " + rutaArchivo.toAbsolutePath());

            documento.save(rutaArchivo.toAbsolutePath().toString());

            return rutaReportes.relativize(rutaArchivo).toString();
        }

        return null;
    }


    private void agregarTitulo(Page page, String titulo) {
        TextFragment textFragment = new TextFragment(titulo);
        textFragment.getTextState().setFontSize(18);
        textFragment.getTextState().setForegroundColor(Color.getBlack());
        page.getParagraphs().add(textFragment);
    }

    private void agregarDatos(Page page, Map<String, Integer> datos) {
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<br>");

        htmlContent.append("<table style='width: 100%; font-family: Monospaced; color: black;'>");
        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
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

    public List<Reporte> buscarTodas() {
        return this.reporteRepository.buscarTodos();
    }

    public Optional<Reporte> buscarPorId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("El ID por la heladera no puede ser null o vacío");

        return this.reporteRepository.buscarPorId(id);
    }
}
