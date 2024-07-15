package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.MasCercano;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestHeladerasMasCercanas {
    Heladera heladeraFallada = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-34.603722, -58.381592)),50);
    Heladera heladera1 = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-34.615803, -58.433298)),50);
    Heladera heladera2 = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-31.420083, -64.188776)),50);
    Heladera heladera3 = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-24.782932, -65.423197)),50);
    Heladera heladera4 = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-34.615800, -58.433290)),50);
    Heladera heladera5 = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-54.801912, -68.302951)),50);
    Heladera heladera6 = Heladera.with("", Direccion.with(new Calle(""),0, new Ubicacion(-34.615810, -58.433280)),50);
    @Test
    @DisplayName("El tecnico mas cercano a una heladera es el que tenga menor distancia entre las dos ubicaciones restando el radio de su area de cobertura")
    public void heladerasMasCercanas(){

        //orden de la lista ordenada por cercania: 1,4,6,2,3,5
        // pero 4 esta llena => 1,6,2,3,5
        //cantidad de viandas a meter: 40
        //heladera1 tiene espacio para: 5
        //cantidad de viandas a meter: 35
        //heladera6 tiene espacio para: 40
        //cantidad de viandas a meter: 0
        //heladera6 tiene 45 viandas
        //heladeras seleccionadas : 1,6
        heladeraFallada.setEstado(EstadoHeladera.INACTIVA);
        heladera1.setEstado(EstadoHeladera.ACTIVA);
        heladera2.setEstado(EstadoHeladera.ACTIVA);
        heladera3.setEstado(EstadoHeladera.ACTIVA);
        heladera4.setEstado(EstadoHeladera.ACTIVA);
        heladera5.setEstado(EstadoHeladera.ACTIVA);
        heladera6.setEstado(EstadoHeladera.ACTIVA);

        heladeraFallada.setViandas(40);
        heladera1.setViandas(45);
        heladera2.setViandas(10);
        heladera3.setViandas(20);
        heladera4.setViandas(50);
        heladera5.setViandas(40);
        heladera6.setViandas(10);

        HeladeraRepository.agregar(heladeraFallada);
        HeladeraRepository.agregar(heladera1);
        HeladeraRepository.agregar(heladera2);
        HeladeraRepository.agregar(heladera3);
        HeladeraRepository.agregar(heladera4);
        HeladeraRepository.agregar(heladera5);
        HeladeraRepository.agregar(heladera6);


        //Assertions.assertEquals(MasCercano.heladerasMasCercanasA(heladeraFallada),);

    }
}
