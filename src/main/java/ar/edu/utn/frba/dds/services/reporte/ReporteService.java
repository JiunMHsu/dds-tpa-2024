package ar.edu.utn.frba.dds.services.reporte;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.reporte.ReporteRepository;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import ar.edu.utn.frba.dds.utils.AppProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
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

        return ReporteService.de(
                reporteRepository,
                donacionViandaRepository,
                registroMovimiento,
                AppProperties.getInstance().propertyFromName("REPORT_DIR"));
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
        List<DonacionVianda> donaciones = donacionViandaRepository.obtenerAPartirDe(haceUnaSemana);

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

    public void generarReporteSemanal() {
        Map<String, Integer> incidentesPorHeladera = this.incidentesPorHeladera();
        Map<String, Integer> donacionPorColaborador = this.donacionesPorColaborador();
        Map<String, Integer> viandasAgregadas = registroMovimiento.getViandasAgregadas();
        Map<String, Integer> viandasQuitadas = registroMovimiento.getViandasQuitadas();

        // crearPDF("Fallas por Heladera", "FallasDeHeladeras", incidentesPorHeladera);
        // crearPDF("Viandas Donadas por Colaborador", "ViandasDonadas", donacionPorColaborador);
        // crearPDFCombinado("Cantidad por Viandas Retiradas/Colocadas", "MovimientoDeViandas", viandasQuitadas, viandasAgregadas);


        registroMovimiento.vaciarRegistro();

        System.out.println("Reportes generados correctamente.");
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
