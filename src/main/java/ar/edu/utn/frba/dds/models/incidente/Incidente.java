package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;

public interface Incidente {

  TipoIncidente getTipo();

  Heladera getHeladera();

  LocalDateTime getFechaHora();
}
