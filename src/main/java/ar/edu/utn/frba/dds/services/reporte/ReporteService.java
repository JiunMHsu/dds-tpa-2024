package ar.edu.utn.frba.dds.services.reporte;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.aperturaHeladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.EstadoDistribucion;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.RetiroDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.reporte.ReporteRepository;
import ar.edu.utn.frba.dds.utils.AppProperties;
import ar.edu.utn.frba.dds.utils.IPDFGenerator;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Servicio de reportes.
 */
public class ReporteService implements WithSimplePersistenceUnit {

  private final ReporteRepository reporteRepository;
  private final DonacionViandaRepository donacionViandaRepository;
  private final IncidenteRepository incidenteRepository;
  private final DistribucionViandasRepository distribucionViandasRepository;
  private final RetiroDeViandaRepository retiroDeViandaRepository;

  /**
   * Crea un servicio de reportes.
   *
   * @param reporteRepository             Repositorio de reportes.
   * @param donacionViandaRepository      Repositorio de donaciones de viandas.
   * @param incidenteRepository           Repositorio de incidentes.
   * @param distribucionViandasRepository Repositorio de distribuciones de viandas.
   * @param retiroDeViandaRepository      Repositorio de retiros de viandas.
   */
  public ReporteService(ReporteRepository reporteRepository,
                        DonacionViandaRepository donacionViandaRepository,
                        IncidenteRepository incidenteRepository,
                        DistribucionViandasRepository distribucionViandasRepository,
                        RetiroDeViandaRepository retiroDeViandaRepository) {
    this.reporteRepository = reporteRepository;
    this.donacionViandaRepository = donacionViandaRepository;
    this.incidenteRepository = incidenteRepository;
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.retiroDeViandaRepository = retiroDeViandaRepository;
  }

  private Map<String, Integer> incidentesPorHeladera() {
    LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
    List<Incidente> incidentes = incidenteRepository.buscarDesde(haceUnaSemana);

    Map<String, Integer> incidentesPorHeladera = new HashMap<>();

    for (Incidente incidente : incidentes) {
      int cantidad = incidentesPorHeladera
          .getOrDefault(incidente.getHeladera().getNombre(), 0) + 1;

      incidentesPorHeladera.put(incidente.getHeladera().getNombre(), cantidad);
    }

    return incidentesPorHeladera;
  }

  private Map<String, Integer> donacionesPorColaborador() {
    LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
    List<DonacionVianda> donaciones = donacionViandaRepository.buscarDesde(haceUnaSemana);

    Map<String, Integer> viandasPorColaborador = new HashMap<>();
    for (DonacionVianda donacion : donaciones) {
      // Tampoco es la forma más segura, habría que hacer por email o algo así
      String key = donacion.getColaborador().getNombre() + " "
          + donacion.getColaborador().getApellido() + " "
          + donacion.getColaborador().getUsuario().getEmail();

      int cantidad = viandasPorColaborador.getOrDefault(key, 0) + 1;
      viandasPorColaborador.put(key, cantidad);
    }

    return viandasPorColaborador;
  }

  private Map<String, Map<String, Integer>> movimientosPorHeladera() {
    LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);

    List<DonacionVianda> donaciones = donacionViandaRepository
        .buscarDesde(haceUnaSemana)
        .stream().filter(DonacionVianda::getEsEntregada).toList();

    List<DistribucionViandas> distribuciones = distribucionViandasRepository
        .buscarDesde(haceUnaSemana)
        .stream().filter(d -> d.getEstado().equals(EstadoDistribucion.COMPLETADA))
        .toList();

    List<RetiroDeVianda> retiros = retiroDeViandaRepository.buscarDesde(haceUnaSemana);

    return getMovimientos(donaciones, distribuciones, retiros);
  }

  private static @NotNull Map<String, Map<String, Integer>> getMovimientos(
      List<DonacionVianda> donaciones,
      List<DistribucionViandas> distribuciones,
      List<RetiroDeVianda> retiros) {

    Map<String, Integer> viandasAgregadas = new HashMap<>();
    Map<String, Integer> viandasQuitadas = new HashMap<>();

    for (DonacionVianda donacion : donaciones) {
      String key = donacion.getHeladera().getId().toString();
      int entradas = viandasAgregadas.getOrDefault(key, 0) + 1;
      viandasAgregadas.put(key, entradas);
    }

    for (DistribucionViandas distribucion : distribuciones) {
      String key = distribucion.getOrigen().getId().toString();

      int entradas = viandasAgregadas.getOrDefault(key, 0) + distribucion.getViandas();
      viandasAgregadas.put(key, entradas);
      int salidas = viandasQuitadas.getOrDefault(key, 0) + distribucion.getViandas();
      viandasQuitadas.put(key, salidas);
    }

    for (RetiroDeVianda retiro : retiros) {
      String key = retiro.getHeladera().getId().toString();
      int salidas = viandasQuitadas.getOrDefault(key, 0) + 1;
      viandasQuitadas.put(key, salidas);
    }

    Map<String, Map<String, Integer>> movimientos = new HashMap<>();

    movimientos.put("Viandas Agregadas", viandasAgregadas);
    movimientos.put("Viandas Quitadas", viandasQuitadas);
    return movimientos;
  }

  /**
   * Genera un reporte semanal.
   *
   * @param pdfGenerator Generador de PDF.
   */
  public void generarReporteSemanal(IPDFGenerator pdfGenerator) {
    Map<String, Integer> incidentesPorHeladera = this.incidentesPorHeladera();
    Map<String, Integer> donacionPorColaborador = this.donacionesPorColaborador();
    Map<String, Map<String, Integer>> movimientos = movimientosPorHeladera();

    final String pathReporteFalla = pdfGenerator.generateDocument(
        "Fallas de Heladera",
        incidentesPorHeladera
    );

    final String reporteDonaciones = pdfGenerator.generateDocument(
        "Viandas Donadas por Colaborador",
        donacionPorColaborador
    );

    final String reporteMovimientos = pdfGenerator.generateDocumentWithSections(
        "Movimiento de Viandas",
        movimientos
    );

    beginTransaction();
    reporteRepository.guardar(Reporte.de("Fallas de Heladera", pathReporteFalla));
    reporteRepository.guardar(Reporte.de("Viandas Donadas por Colaborador", reporteDonaciones));
    reporteRepository.guardar(Reporte.de("Movimiento de Viandas", reporteMovimientos));
    commitTransaction();

    System.out.println("Reportes generados correctamente.");
  }

  /**
   * Busca todos los reportes.
   *
   * @return Lista de reportes.
   */
  public List<Reporte> buscarTodas() {
    return this.reporteRepository.buscarTodos();
  }

  /**
   * Busca un reporte por ID.
   *
   * @param id Id del reporte.
   * @return Reporte encontrado.
   */
  public Reporte buscarPorId(@NotNull String id) {
    return this.reporteRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Busca un reporte.
   *
   * @param reporte Reporte a buscar.
   * @return InputStream del reporte.
   * @throws FileNotFoundException Si no se encuentra el archivo.
   */
  public InputStream buscarReporte(Reporte reporte) throws FileNotFoundException {
    Path path = Path.of(
        AppProperties.getInstance().propertyFromName("REPORT_DIR"),
        reporte.getNombreArchivo()).toAbsolutePath();

    return new FileInputStream(path.toString());
  }
}
