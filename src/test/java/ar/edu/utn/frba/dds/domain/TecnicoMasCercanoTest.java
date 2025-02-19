package ar.edu.utn.frba.dds.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.services.suscripcion.FallaHeladeraService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TecnicoMasCercanoTest {

  private FallaHeladeraService fallaHeladeraService;
  private Heladera heladera;
  private Tecnico tecnico1;
  private Tecnico tecnico2;
  private Tecnico tecnico3;


  @BeforeEach
  public void setUp() {

    Barrio flores = new Barrio("Flores");

    heladera = Heladera.con("heladerafallida", Direccion.con(flores, new Calle(""), 0, new Ubicacion(-34.603722, -58.381592)), 50, new RangoTemperatura(), 40, "");

    tecnico1 = Tecnico.con(null, "", "", null, "", Contacto.vacio(), Area.con(new Ubicacion(-34.615803, -58.433298), 9, new Barrio("Flores")));
    tecnico2 = Tecnico.con(null, "", "", null, "", Contacto.vacio(), Area.con(new Ubicacion(-34.615803, -58.433298), 6, new Barrio("Flores")));
    tecnico3 = Tecnico.con(null, "", "", null, "", Contacto.vacio(), Area.con(new Ubicacion(-31.420083, -64.188776), 5, new Barrio("Flores")));

    TecnicoService tecnicoService = mock(TecnicoService.class);
    when(tecnicoService.obtenerPorBarrio(flores)).thenReturn(List.of(tecnico3, tecnico2, tecnico1));

    fallaHeladeraService = new FallaHeladeraService(null, null, null, tecnicoService, null, null);
  }

  @Test
  @DisplayName("El tecnico más cercano a una direccion, es el que sea del mismo barrio y tenga menor distancia entre las dos ubicaciones restando el radio de su área de cobertura")
  public void tecnicoMasCercano() {
    Tecnico tecnico = fallaHeladeraService.tecnicoMasCercano(heladera.getDireccion());

    Assertions.assertEquals(tecnico1, tecnico);
  }
}
