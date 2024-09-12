package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTecnicoMasCercano {

  Tecnico tecnico1 = Tecnico.with("1", Contacto.empty(), new Area(new Ubicacion(-34.615803, -58.433298), 9.0));
  Tecnico tecnico2 = Tecnico.with("2", Contacto.empty(), new Area(new Ubicacion(-34.615803, -58.433298), 6.0));
  Tecnico tecnico3 = Tecnico.with("3", Contacto.empty(), new Area(new Ubicacion(-31.420083, -64.188776), 5.0));

  Heladera heladera = Heladera.con("", Direccion.with(new Calle(""), 0, new Ubicacion(-34.603722, -58.381592)), 70);

  @Test
  @DisplayName("El tecnico mas cercano a una heladera es el que tenga menor distancia entre las dos ubicaciones restando el radio de su area de cobertura")
  public void tecnicoMasCercano() {
    List<Tecnico> listaTecnicos = new ArrayList<>();
    listaTecnicos.add(tecnico1);
    listaTecnicos.add(tecnico2);
    listaTecnicos.add(tecnico3);

    Assertions.assertEquals(tecnico1, heladera.tecnicoMasCercano(listaTecnicos));
  }
}
