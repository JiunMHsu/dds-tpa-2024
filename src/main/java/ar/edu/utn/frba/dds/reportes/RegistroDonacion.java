package ar.edu.utn.frba.dds.reportes;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.repository.colaboracion.DonacionViandaRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroDonacion {
    private DonacionViandaRepository donacionViandaRepository;

    public RegistroDonacion() {
        this.donacionViandaRepository = new DonacionViandaRepository();
    }

    public Map<String, Integer> donacionesPorColaborador() {
        LocalDate haceUnaSemana = LocalDate.now().minusWeeks(1);
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
