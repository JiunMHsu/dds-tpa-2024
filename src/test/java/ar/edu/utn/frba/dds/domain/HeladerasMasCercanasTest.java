package ar.edu.utn.frba.dds.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeladerasMasCercanasTest {
  private FallaHeladeraService fallaHeladeraService;
  private Heladera heladera;
  private Heladera heladera1;
  private Heladera heladera2;
  private Heladera heladera3;
  private Heladera heladera4;
  private Heladera heladera5;
  private Heladera heladera6;

  @BeforeEach
  public void setUp() {

    Barrio flores = new Barrio("Flores");

    heladera = Heladera.con("heladerafallida", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-34.603722, -58.381592)), 50, new RangoTemperatura(), 40, "");
    heladera1 = Heladera.con("heladera1", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-34.617200, -58.434700)), 50, new RangoTemperatura(), 30, "");
    heladera2 = Heladera.con("heladera2", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-31.420083, -64.188776)), 50, new RangoTemperatura(), 10, "");
    heladera3 = Heladera.con("heladera3", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-24.782932, -65.423197)), 50, new RangoTemperatura(), 20, "");
    heladera4 = Heladera.con("heladera4", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-34.614500, -58.432000)), 50, new RangoTemperatura(), 50, "");
    heladera5 = Heladera.con("heladera5", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-54.801912, -68.302951)), 50, new RangoTemperatura(), 40, "");
    heladera6 = Heladera.con("heladera6", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-34.616000, -58.433500)), 50, new RangoTemperatura(), 45, "");

    HeladeraService heladeraService = mock(HeladeraService.class);
    when(heladeraService.buscarPorBarrio(flores)).thenReturn(List.of(heladera1, heladera2, heladera3, heladera4, heladera5, heladera6));

    fallaHeladeraService = new FallaHeladeraService(null, null, null, null, heladeraService, null);
  }

  @Test
  @DisplayName("Segun una heladera, retorna las heladeras mas cercanas por barrio, ordenadas por menor distancia")
  public void heladerasMasCercanas() {
    List<Heladera> heladerasCercanas = fallaHeladeraService.heladerasActivasMasCercanas(heladera);

    Assertions.assertIterableEquals(List.of(heladera4, heladera6, heladera1, heladera2, heladera3, heladera5), heladerasCercanas);
  }

  @Test
  @DisplayName("Segun la cantidad de viandas a transportar de la heladera, retorna una lista de las heladeras recomendadas necesarias")
  public void heladerasRecomendadas() {
    List<Heladera> heladerasRecomendadas = fallaHeladeraService.heladerasRecomendadas(heladera);

    //lista heladeras cercanas (del mismo barrio): 4,6,1,2,3,5
    // pero 4 esta llena => 6,1,2,3,5
    //cantidad nueva viandas a meter: 40
    //heladera6 tiene espacio para: 5
    //cantidad nueva viandas a meter: 35
    //heladera1 tiene espacio para: 20
    //cantidad nueva viandas a meter: 15
    //heladera2 tiene espacio para: 40
    //heladera2 quedaria con 25 viandas
    //heladeras seleccionadas : 6,1,2

    Assertions.assertIterableEquals(List.of(heladera6, heladera1, heladera2), heladerasRecomendadas);
  }
}