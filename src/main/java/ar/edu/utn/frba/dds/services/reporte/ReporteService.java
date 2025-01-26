package ar.edu.utn.frba.dds.services.reporte;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
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
import java.util.Optional;
import lombok.Builder;

@Builder
public class ReporteService implements WithSimplePersistenceUnit {

  private final ReporteRepository reporteRepository;
  private final DonacionViandaRepository donacionViandaRepository;
  private final IncidenteRepository incidenteRepository;
  private final DistribucionViandasRepository distribucionViandasRepository;
  private final RetiroDeViandaRepository retiroDeViandaRepository;

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
    List<Incidente> incidentes = incidenteRepository.buscarAPartirDe(haceUnaSemana);

    Map<String, Integer> incidentesPorHeladera = new HashMap<>();

    for (Incidente incidente : incidentes) {
      int cantidad = incidentesPorHeladera.getOrDefault(incidente.getHeladera().getNombre(), 0) + 1;
      incidentesPorHeladera.put(incidente.getHeladera().getNombre(), cantidad);
    }

    return incidentesPorHeladera;
  }

  private Map<String, Integer> donacionesPorColaborador() {
    LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
    List<DonacionVianda> donaciones = donacionViandaRepository.buscarAPartirDe(haceUnaSemana);

    Map<String, Integer> viandasPorColaborador = new HashMap<>();
    for (DonacionVianda donacion : donaciones) {
      // Tampoco es la forma más segura, habría que hacer nueva email o algo así
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
    List<DistribucionViandas> distribuciones = distribucionViandasRepository.buscarAPartirDe(haceUnaSemana);
    List<RetiroDeVianda> retiros = retiroDeViandaRepository.buscarAPartirDe(haceUnaSemana);

    Map<String, Map<String, Integer>> movimientos = new HashMap<>();
    Map<String, Integer> viandasAgregadas = new HashMap<>();
    Map<String, Integer> viandasQuitadas = new HashMap<>();

    for (DistribucionViandas distribucion : distribuciones) {
      String key = distribucion.getOrigen().getId().toString();

      int entradas = viandasAgregadas.getOrDefault(key, 0) + 1;
      viandasAgregadas.put(key, entradas);
      int salidas = viandasQuitadas.getOrDefault(key, 0) + 1;
      viandasQuitadas.put(key, salidas);
    }

    for (RetiroDeVianda retiro : retiros) {
      String key = retiro.getHeladera().getId().toString();
      int salidas = viandasQuitadas.getOrDefault(key, 0) + 1;
      viandasQuitadas.put(key, salidas);
    }

    movimientos.put("Viandas Agregadas", viandasAgregadas);
    movimientos.put("Viandas Quitadas", viandasQuitadas);
    return movimientos;
  }

  public void generarReporteSemanal(IPDFGenerator pdfGenerator) {
    Map<String, Integer> incidentesPorHeladera = this.incidentesPorHeladera();
    Map<String, Integer> donacionPorColaborador = this.donacionesPorColaborador();
    Map<String, Map<String, Integer>> movimientos = movimientosPorHeladera();

    String pathReporteFalla = pdfGenerator.generateDocument("Fallas nueva Heladera", incidentesPorHeladera);
    String reporteDonaciones = pdfGenerator.generateDocument("Viandas Donadas nueva Colaborador", donacionPorColaborador);
    String reporteMovimientos = pdfGenerator.generateDocumentWithSections("Movimiento de Viandas", movimientos);

    beginTransaction();
    reporteRepository.guardar(Reporte.de("Fallas nueva Heladera", pathReporteFalla));
    reporteRepository.guardar(Reporte.de("Viandas Donadas nueva Colaborador", reporteDonaciones));
    reporteRepository.guardar(Reporte.de("Movimiento de Viandas", reporteMovimientos));
    commitTransaction();

    System.out.println("Reportes generados correctamente.");
  }

  public List<Reporte> buscarTodas() {
    return this.reporteRepository.buscarTodos();
  }

  public Optional<Reporte> buscarPorId(String id) {
    if (id == null || id.isEmpty())
      throw new IllegalArgumentException("El ID nueva la heladera no puede ser null o vacío");

    return this.reporteRepository.buscarPorId(id);
  }

  public InputStream buscarReporte(Reporte reporte) throws FileNotFoundException {
    Path path = Path.of(
        AppProperties.getInstance().propertyFromName("REPORT_DIR"),
        reporte.getNombreArchivo()).toAbsolutePath();

    return new FileInputStream(path.toString());
  }
}
