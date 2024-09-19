package ar.edu.utn.frba.dds.domain;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHeladerasMasCercanas {

    HeladeraRepository heladeraRepository = new HeladeraRepository();

    Heladera heladeraFallada = Heladera.con("fallada", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-34.603722, -58.381592)), 50);
    Heladera heladera1 = Heladera.con("1", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-34.615803, -58.433298)), 50);
    Heladera heladera2 = Heladera.con("2", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-31.420083, -64.188776)), 50);
    Heladera heladera3 = Heladera.con("3", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-24.782932, -65.423197)), 50);
    Heladera heladera4 = Heladera.con("4", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-34.615800, -58.433290)), 50);
    Heladera heladera5 = Heladera.con("5", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-54.801912, -68.302951)), 50);
    Heladera heladera6 = Heladera.con("6", Direccion.with(new Barrio(""), new Calle(""), 0, new Ubicacion(-34.615810, -58.433280)), 50);

    @Test
    @DisplayName("El tecnico mas cercano a una heladera es el que tenga menor distancia entre las dos ubicaciones restando el radio de su area de cobertura")
    public void heladerasMasCercanas() {

        //orden de la lista ordenada por cercania: 6, 1,4,2,3,5
        // pero 4 esta llena => 6,1, 2,3,5
        //cantidad de viandas a meter: 40
        //heladera6 tiene espacio para: 5
        //cantidad de viandas a meter: 35
        //heladera1 tiene espacio para: 40
        //cantidad de viandas a meter: 0
        //heladera1 tiene 45 viandas
        //heladeras seleccionadas : 6,1
        heladeraFallada.setEstado(EstadoHeladera.INACTIVA);
        heladera1.setEstado(EstadoHeladera.ACTIVA);
        heladera2.setEstado(EstadoHeladera.ACTIVA);
        heladera3.setEstado(EstadoHeladera.ACTIVA);
        heladera4.setEstado(EstadoHeladera.ACTIVA);
        heladera5.setEstado(EstadoHeladera.ACTIVA);
        heladera6.setEstado(EstadoHeladera.ACTIVA);

        heladeraFallada.setViandas(40);
        heladera1.setViandas(10);
        heladera2.setViandas(30);
        heladera3.setViandas(20);
        heladera4.setViandas(50);
        heladera5.setViandas(40);
        heladera6.setViandas(45);

        heladeraRepository.guardar(heladera1);
        heladeraRepository.guardar(heladera2);
        heladeraRepository.guardar(heladera3);
        heladeraRepository.guardar(heladera4);
        heladeraRepository.guardar(heladera5);
        heladeraRepository.guardar(heladera6);

        List<Heladera> heladerasSeleccionadas = new ArrayList<>();
        heladerasSeleccionadas.add(heladera1);
        heladerasSeleccionadas.add(heladera6);

//    Incidente incidente = Incidente.de(TipoIncidente.FRAUDE,heladeraFallada, LocalDateTime.now());
//
//    Assertions.assertTrue(incidente.heladerasRecomendadasPorFalla().containsAll(heladerasSeleccionadas));

    }
}
