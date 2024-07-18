package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Area;
import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.incidente.Incidente;
import ar.edu.utn.frba.dds.models.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestIncidentes {

  Tecnico tecnico1;
  Tecnico tecnico2;
  Tecnico tecnico3;

  Heladera heladera;

  Incidente incidente;

  @BeforeEach
  public void setup() {
    tecnico1 = Mockito.mock(Tecnico.class);
    tecnico1.setAreaDeCobertura(new Area(new Ubicacion(-34.615803, -58.433298), 9.0));
    TecnicoRepository.agregar(tecnico1);

    tecnico2 = Mockito.mock(Tecnico.class);
    tecnico2.setAreaDeCobertura(new Area(new Ubicacion(-34.615803, -58.433298), 6.0));
    TecnicoRepository.agregar(tecnico2);

    tecnico3 = Mockito.mock(Tecnico.class);
    tecnico3.setAreaDeCobertura(new Area(new Ubicacion(-31.420083, -64.188776), 5.0));
    TecnicoRepository.agregar(tecnico3);

    heladera = Heladera.with("Medrano UTN",
        Direccion.with(new Calle(""), 0, new Ubicacion(-34.603722, -58.381592)), 70);
  }

  @Test
  @DisplayName("Cuando un incidente es reportado, el Técnico más cercano es notificado.")
  public void avisoTecnico() {
    incidente = Incidente.of(TipoIncidente.FRAUDE, heladera, LocalDateTime.now());
    incidente.reportar();
  }

}
