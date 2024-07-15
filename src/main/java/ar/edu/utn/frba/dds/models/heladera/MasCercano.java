package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class MasCercano {

    public static List<Heladera> heladerasMasCercanasA(Heladera heladera){
        List<Heladera> listaHeladerasActivasConEspacio = HeladeraRepository.obtenerTodos().stream()
                .filter(Heladera::estaActiva)
                .filter(Heladera::noEstaLlena)
                .toList();
        List<Heladera> listaHeladerasOrdenadasPorCercania = listaHeladerasActivasConEspacio.stream()
                .sorted(Comparator.comparingDouble(heladera1 -> heladera1.getDireccion().getUbicacion().calcularDistanciaEntreUbicaciones(heladera.getDireccion().getUbicacion())))
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
}
