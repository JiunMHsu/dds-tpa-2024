package ar.edu.utn.frba.dds.models.tecnico;

import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class MasCercano {

    public List<Heladera> heladerasMasCercanasA(Heladera heladera){
        List<Heladera> listaHeladerasOrdenadasPorCercania = HeladeraRepository.obtenerTodos().stream()
                .sorted(Comparator.comparingDouble(heladera1 -> calcularDistanciaEntreUbicaciones(heladera1.getDireccion().getUbicacion(), heladera.getDireccion().getUbicacion())))
                .toList();
        List<Heladera> heladerasSeleccionadas = new ArrayList<>();
        Integer cantViandasATransportar = heladera.getViandas();
        for (Heladera heladera1 : listaHeladerasOrdenadasPorCercania) {
            if (cantViandasATransportar <= 0) {
                break;
            }
            heladerasSeleccionadas.add(heladera1);
            cantViandasATransportar -= heladera1.espacioRestante();
        }
        return heladerasSeleccionadas;
    }
    // sera mejor el filtro en el TecnicoRepository, no creo
    public Tecnico tecnicoMasCercanoA(Heladera heladera) {
        List<Tecnico> listaTecnicos = TecnicoRepository.obtenerTodos();
        return listaTecnicos.stream()
                .min(Comparator.comparingDouble(tecnico -> MasCercano.calcularDistanciaTecnicoHeladera(tecnico.getAreaDeCobertura(), heladera.getDireccion().getUbicacion())))
                .orElseThrow(() -> new RuntimeException("No se encontró ningún técnico."));
    }
    public static double calcularDistanciaTecnicoHeladera(Area areaTecnico , Ubicacion ubicHeladera){
        double distanciaEntreCoordenadas = MasCercano.calcularDistanciaEntreUbicaciones(areaTecnico.getUbicacion(), ubicHeladera);
        return distanciaEntreCoordenadas - areaTecnico.getRadio();
    }
    public static double calcularDistanciaEntreUbicaciones(Ubicacion u1, Ubicacion u2) {
        final int RADIO_TIERRA_KM = 6371;

        double lat1 = Math.toRadians(u1.getLatitud());
        double lon1 = Math.toRadians(u1.getLongitud());
        double lat2 = Math.toRadians(u2.getLatitud());
        double lon2 = Math.toRadians(u2.getLongitud());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

}
