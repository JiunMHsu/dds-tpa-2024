package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.incidente.Alerta;
import ar.edu.utn.frba.dds.models.incidente.FallaTecnica;
import ar.edu.utn.frba.dds.repository.incidente.AlertaRepository;
import ar.edu.utn.frba.dds.repository.incidente.FallaTecnicaRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroIncidente {

    private final AlertaRepository alertaRepository;
    private final FallaTecnicaRepository fallaTecnicaRepository;


    public RegistroIncidente() {
        this.alertaRepository = new AlertaRepository();
        this.fallaTecnicaRepository = new FallaTecnicaRepository();
    }

    public Map<String, Integer> incidentesPorHeladera() {

        LocalDateTime haceUnaSemana = LocalDateTime.now().minusWeeks(1);
        List<Alerta> alertas = alertaRepository.obtenerAPartirDe(haceUnaSemana);
        List<FallaTecnica> fallasTecnicas = fallaTecnicaRepository.obtenerAPartirDe(haceUnaSemana);

        Map<String, Integer> incidentesPorHeladera = new HashMap<>();

        for (Alerta alerta : alertas) {
            int cantidad = incidentesPorHeladera.getOrDefault(alerta.getHeladera().getNombre(), 0) + 1;
            incidentesPorHeladera.put(alerta.getHeladera().getNombre(), cantidad);
        }

        for (FallaTecnica fallaTecnica : fallasTecnicas) {
            int cantidad = incidentesPorHeladera.getOrDefault(fallaTecnica.getHeladera().getNombre(), 0) + 1;
            incidentesPorHeladera.put(fallaTecnica.getHeladera().getNombre(), cantidad);
        }

        return incidentesPorHeladera;
    }
}
