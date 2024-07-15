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
    // sera mejor el filtro en el TecnicoRepository, no creo


}
