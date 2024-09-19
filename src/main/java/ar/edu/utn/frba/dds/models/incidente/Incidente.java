package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;

// TODO - Pasar a clase abstracta - Estrategia Single Table
public interface Incidente {

    TipoIncidente getTipo();

    Heladera getHeladera();

    LocalDateTime getFechaHora();
}
