package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroDonacion {
    private final DonacionViandaRepository donacionViandaRepository;

    public RegistroDonacion() {
        this.donacionViandaRepository = new DonacionViandaRepository();
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
}
